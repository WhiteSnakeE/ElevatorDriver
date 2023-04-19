package com.sytoss.edu.elevator.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HouseConverter {

    private final HouseBuilder houseBuilder;

    private final ShaftConverter shaftConverter;

    public HouseDTO toDTO(House house, List<SequenceOfStops> orderSequenceOfStops) {
        return HouseDTO.builder().numberOfFloors(house.getFloors().size())
                .numberOfShafts(house.getShafts().size()).orderSequenceOfStops(orderSequenceToStringInJSON(orderSequenceOfStops)).build();
    }

    public House fromDTO(HouseDTO houseDTO, List<ShaftDTO> shaftDTOList) {
        House house = houseBuilder.build(houseDTO.getNumberOfShafts(), houseDTO.getNumberOfFloors());
        house.setId(houseDTO.getId());
        house.getShafts().clear();
        for (ShaftDTO shaftDTO : shaftDTOList) {
            Shaft shaft = shaftConverter.fromDTO(shaftDTO);
            house.getShafts().add(shaft);
        }
        return house;
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

    public List<SequenceOfStops> stringJSONToOrderSequence(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        List<SequenceOfStops> sequence = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            sequence = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, SequenceOfStops.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return sequence;
    }
}
