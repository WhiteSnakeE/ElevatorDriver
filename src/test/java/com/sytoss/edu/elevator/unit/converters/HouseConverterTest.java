package com.sytoss.edu.elevator.unit.converters;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class HouseConverterTest {

    private final HouseConverter houseConverter = new HouseConverter();

    private final CommandManager commandManager = mock(CommandManager.class);

    private final HouseBuilder houseBuilder = new HouseBuilder(commandManager);

    @Test
    public void toDTOTest() {
        House houseTmp = houseBuilder.build(2, 16);

        List<SequenceOfStops> order = new ArrayList<>();
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(List.of(1, 2, 3));
        sequence.setDirection(Direction.UPWARDS);
        sequence.setId(123L);
        order.add(sequence);

        HouseDTO houseDTO = houseConverter.toDTO(houseTmp, order);

        Assertions.assertEquals(2, houseDTO.getNumberOfShafts());
        Assertions.assertEquals(16, houseDTO.getNumberOfFloors());
        Assertions.assertEquals("[{\"id\":123,\"stopFloors\":[1,2,3],\"direction\":\"UPWARDS\"}]", houseDTO.getOrderSequenceOfStops());
    }
}
