package com.sytoss.edu.elevator.unit.converters;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.bom.enums.OverWeightState;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.spy;

public class ShaftConverterTest {

    private final ShaftConverter shaftConverter = new ShaftConverter();

    @Test
    public void toDTOTest() {
        HouseDTO houseDTO = spy(HouseDTO.class);
        houseDTO.setId(138277872);
        houseDTO.setNumberOfShafts(2);
        houseDTO.setNumberOfFloors(16);

        Shaft shaft = spy(Shaft.class);
        ShaftDTO shaftDTO = shaftConverter.toDTO(shaft, houseDTO);

        Assertions.assertEquals(shaft.getId(), shaftDTO.getId());
        Assertions.assertEquals(shaft.getEngine().getEngineState(), EngineState.valueOf(shaftDTO.getEngineState()));
        Assertions.assertEquals(shaft.getCabin().getDoorState(), DoorState.valueOf(shaftDTO.getDoorState()));
        Assertions.assertEquals(shaft.getCabin().getOverWeightState(), OverWeightState.valueOf(shaftDTO.getOverweightState()));
        Assertions.assertEquals(shaft.getCabinPosition(), shaftDTO.getCabinPosition());
        Assertions.assertEquals(houseDTO.getId(), shaftDTO.getHouseDTO().getId());
    }
}
