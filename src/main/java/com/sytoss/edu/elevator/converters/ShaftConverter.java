package com.sytoss.edu.elevator.converters;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import org.springframework.stereotype.Component;

@Component
public class ShaftConverter {

    public ShaftDTO toDTO(Shaft shaft, HouseDTO houseDTO) {
        return ShaftDTO.builder().id(shaft.getId()).cabinPosition(shaft.getCabinPosition()).doorState(shaft.getCabin()
                        .getDoorState().toString()).engineState(shaft.getEngine().getEngineState().toString())
                .overweightState(shaft.getCabin().getOverWeightState().toString()).houseDTO(houseDTO).build();
    }
}
