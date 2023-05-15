package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.params.HouseParams;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;

    private final ShaftRepository shaftRepository;

    private final HouseBuilder houseBuilder;

    private final HouseConverter houseConverter;

    private final ShaftConverter shaftConverter;

    private final CommandManager commandManager;

    private final HouseThreadPool houseThreadPool;

    private House changeHouseConfiguration(int shaftsCount, int floorsCount) {
        House house = houseBuilder.build(shaftsCount, floorsCount);
        for (Shaft shaft : house.getShafts()) {
            shaft.addShaftListener(house.getElevatorDriver());
        }
        return house;
    }

    public void saveRequest(HouseParams houseParams) {
        House house = changeHouseConfiguration(houseParams.getNumberOfShafts(), houseParams.getNumberOfFloors());
        HouseDTO houseDTO = houseConverter.toDTO(house, house.getElevatorDriver().getOrderSequenceOfStops());
        houseRepository.save(houseDTO);
        house.setId(houseDTO.getId());

        for (Shaft shaft : house.getShafts()) {
            ShaftDTO shaftDTO = shaftConverter.toDTO(shaft, houseDTO);
            shaftRepository.save(shaftDTO);
            shaft.setId(shaftDTO.getId());
        }
    }

    public House getHouse(long houseId) {
        HouseDTO houseDTO = getHouseDTO(houseId);
        List<ShaftDTO> shaftDTOList = shaftRepository.findByHouseDTOId(houseDTO.getId());
        House house = houseConverter.fromDTO(houseDTO, shaftDTOList);
        setListeners(house);
        for (Shaft shaft : house.getShafts()) {
            if (shaft.getSequenceOfStops() != null) {
                houseThreadPool.getFixedThreadPool().submit(() -> {
                    log.info("startMoveCabin: start threads for shaft with id {}", shaft.getId());
                    HashMap<String, Object> paramsActivateCommand = new HashMap<>();
                    paramsActivateCommand.put(CommandManager.SHAFT_PARAM, shaft);
                    paramsActivateCommand.put(CommandManager.FLOORS_PARAM, house.getFloors());
                    commandManager.getCommand(Command.MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
                    log.info("startMoveCabin: finish threads for shaft with id {}", shaft.getId());
                });
            }
        }
        return house;
    }

    private House getHouseById(long houseId) {
        HouseDTO houseDTO = getHouseDTO(houseId);
        List<ShaftDTO> shaftDTOList = shaftRepository.findByHouseDTOId(houseDTO.getId());
        House house = houseConverter.fromDTO(houseDTO, shaftDTOList);
        setListeners(house);
        house.setElevatorDriver(new ElevatorDriver(commandManager));

        return house;
    }

    public House getHouseByShaftId(long shaftId) {
        long houseId = shaftRepository.getAllById(shaftId).getHouseDTO().getId();
        return getHouseById(houseId);
    }

    public HouseDTO getHouseDTO(long houseId) {
        Optional<HouseDTO> houseDTOOptional = houseRepository.findById(Long.valueOf(houseId));
        return houseDTOOptional.get();
    }

    private void setListeners(House house) {
        for (Shaft shaft : house.getShafts()) {
            shaft.addShaftListener(house.getElevatorDriver());
        }
    }

    public void updateOrderById(Long houseId, List<SequenceOfStops> order) {
        houseRepository.updateOrderById(houseId, JsonUtil.orderSequenceToStringInJSON(order));
    }
}
