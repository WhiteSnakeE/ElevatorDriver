package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class FloorService {

    private final HouseRepository houseRepository;

    private final HouseConverter houseConverter;

    private final ShaftRepository shaftRepository;

    private final CommandManager commandManager;

    private final HouseThreadPool houseThreadPool;



    private House getHouse(int houseId) {
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

    private HouseDTO getHouseDTO(int houseId){
        Optional<HouseDTO> houseDTOOptional = houseRepository.findById(Long.valueOf(houseId));
        return houseDTOOptional.get();
    }

    private void setListeners(House house) {
        for (Shaft shaft : house.getShafts()) {
            shaft.addShaftListener(house.getElevatorDriver());
        }
    }

    public void goUpCabinRequest(int houseNumber,int floorNumber) {
        House house = getHouse(houseNumber);
        HouseDTO houseDTO = getHouseDTO(houseNumber);
        house.getElevatorDriver().setOrderSequenceOfStops(JsonUtil.stringJSONToOrderSequence(houseDTO.getOrderSequenceOfStops()));
        Floor floor = house.getFloors().get(floorNumber - 1);

        if (floor instanceof FloorWithUpButton) {
            ((FloorWithUpButton) floor).pressUpButton(house);
        }
    }
}
