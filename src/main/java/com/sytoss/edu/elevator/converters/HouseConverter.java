package com.sytoss.edu.elevator.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.dto.HouseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HouseConverter {

    public HouseDTO toDTO(House house, List<SequenceOfStops> orderSequenceOfStops) {
        return HouseDTO.builder().numberOfFloors(house.getFloors().size())
                .numberOfShafts(house.getShafts().size()).orderSequenceOfStops(orderSequenceToStringInJSON(orderSequenceOfStops)).build();
    }

    public String orderSequenceToStringInJSON(List<SequenceOfStops> orderSequenceOfStops) {
        if (orderSequenceOfStops == null || orderSequenceOfStops.isEmpty()) {
            return null;
        }

        String json;

        try {
            json = new ObjectMapper().writeValueAsString(orderSequenceOfStops);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json;
    }
}
