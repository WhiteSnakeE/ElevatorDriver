package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.events.OrderSequenceOfStopsChangedEvent;
import com.sytoss.edu.elevator.listeners.OrderSequenceOfStopsListener;
import com.sytoss.edu.elevator.params.HouseParams;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService implements OrderSequenceOfStopsListener {

    private final HouseRepository houseRepository;

    private final ShaftRepository shaftRepository;

    private final HouseBuilder houseBuilder;

    private final HouseConverter houseConverter;

    private final ShaftConverter shaftConverter;

    private final CommandManager commandManager;

    private final ShaftService shaftService;

    private final EngineService engineService;

    private final CabinService cabinService;

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

    @Transactional
    public House getHouseById(long houseId) {
        HouseDTO houseDTO = getHouseDTO(houseId);
        List<ShaftDTO> shaftDTOList = shaftRepository.findByHouseDTOId(houseDTO.getId());
        House house = houseConverter.fromDTO(houseDTO, shaftDTOList);
        house.setElevatorDriver(new ElevatorDriver(commandManager));
        house.getShafts().forEach(shaft -> {
            shaft.addShaftListener(shaftService);
            shaft.addShaftListener(house.getElevatorDriver());
            shaft.getEngine().addEngineListener(engineService);
            shaft.getCabin().addCabinListener(cabinService);
            shaft.getCabin().addCabinListener(house.getElevatorDriver());
        });
        return house;
    }

    @Transactional
    public House getHouseByShaftId(long shaftId) {
        long houseId = shaftRepository.getReferenceById(shaftId).getHouseDTO().getId();
        return getHouseById(houseId);
    }

    @Transactional
    public HouseDTO getHouseDTO(long houseId) {
        return houseRepository.getReferenceById(houseId);
    }

    public void updateOrderById(Long houseId, List<SequenceOfStops> order) {
        houseRepository.updateOrderById(houseId, JsonUtil.orderSequenceToStringInJSON(order));
    }

    @Override
    public void handleOrderSequenceOfStopsChanged(OrderSequenceOfStopsChangedEvent event) {
        House house = event.getHouse();
        houseRepository.updateOrderById(house.getId(), JsonUtil.orderSequenceToStringInJSON(house.getElevatorDriver().getOrderSequenceOfStops()));
    }
}
