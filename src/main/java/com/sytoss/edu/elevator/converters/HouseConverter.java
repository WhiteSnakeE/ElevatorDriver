package com.sytoss.edu.elevator.converters;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.dto.HouseDTO;
import org.springframework.stereotype.Component;

@Component
public class HouseConverter {

    public HouseDTO toDTO(House house) {
        return HouseDTO.builder().id(house.getId()).numberOfFloors(house.getFloors().size()).numberOfShafts(house.getShafts().size()).build();
    }
}
