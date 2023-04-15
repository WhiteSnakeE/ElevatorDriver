package com.sytoss.edu.elevator.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import org.springframework.stereotype.Component;

@Component
public class ShaftConverter {

    public ShaftDTO toDTO(Shaft shaft, HouseDTO houseDTO) {
        return ShaftDTO.builder().cabinPosition(shaft.getCabinPosition()).doorState(shaft.getCabin()
                        .getDoorState()).engineState(shaft.getEngine().getEngineState())
                .overweightState(shaft.getCabin().getOverWeightState()).houseDTO(houseDTO)
                .sequenceOfStops(sequenceToStringInJSON(shaft.getSequenceOfStops())).build();
    }

    public String sequenceToStringInJSON(SequenceOfStops sequenceOfStops) {
        if (sequenceOfStops == null) {
            return null;
        }

        String json;

        try {
            json = new ObjectMapper().writeValueAsString(sequenceOfStops);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json;
    }
}
