package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.params.HouseParams;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;

    private final ShaftRepository shaftRepository;

    private final HouseBuilder houseBuilder;

    private final HouseConverter houseConverter;

    private final ShaftConverter shaftConverter;



    private House changeHouseConfiguration(int shaftsCount, int floorsCount) {
        House house = houseBuilder.build(shaftsCount, floorsCount);
//        house.setFloors(houseTmp.getFloors());
//        house.setShafts(houseTmp.getShafts());

        for (Shaft shaft : house.getShafts()) {
            shaft.addShaftListener(house.getElevatorDriver());
        }
        return house;
    }

    public void saveRequest(HouseParams houseParams) {
        House house =  changeHouseConfiguration(houseParams.getNumberOfShafts(), houseParams.getNumberOfFloors());
        HouseDTO houseDTO = houseConverter.toDTO(house, house.getElevatorDriver().getOrderSequenceOfStops());
        houseRepository.save(houseDTO);
        house.setId(houseDTO.getId());

        for (Shaft shaft : house.getShafts()) {
            ShaftDTO shaftDTO = shaftConverter.toDTO(shaft, houseDTO);
            shaftRepository.save(shaftDTO);
            shaft.setId(shaftDTO.getId());
        }
    }
}
