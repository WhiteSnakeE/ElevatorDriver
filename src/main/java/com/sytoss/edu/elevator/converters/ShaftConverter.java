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

    public Shaft fromDTO(ShaftDTO shaftDTO) {
        Shaft shaft = new Shaft();
        shaft.setId(shaftDTO.getId());
        shaft.setCabinPosition(shaftDTO.getCabinPosition());
        shaft.getEngine().setEngineState(shaftDTO.getEngineState());
        shaft.getCabin().setDoorState(shaftDTO.getDoorState());
        shaft.getCabin().setOverWeightState(shaftDTO.getOverweightState());
        shaft.setSequenceOfStops(stringJSONToSequenceOfStops(shaftDTO.getSequenceOfStops()));

        return shaft;
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

    public SequenceOfStops stringJSONToSequenceOfStops(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        SequenceOfStops sequence = null;

        try {
            sequence = new ObjectMapper().readValue(json, SequenceOfStops.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return sequence;
    }
}
