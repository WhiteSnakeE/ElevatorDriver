package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CommandManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class HouseTest {

    private final HouseBuilder houseBuilder = new HouseBuilder(mock(CommandManager.class));

    private final House house = houseBuilder.build(2, 16);

    @Test
    public void findNearestCabinTest() {
        List<SequenceOfStops> orderSequenceOfStops = new ArrayList<>();
        SequenceOfStops sequence1 = new SequenceOfStops();
        sequence1.setId(123L);
        sequence1.setDirection(Direction.UPWARDS);
        sequence1.setStopFloors(List.of(3));
        orderSequenceOfStops.add(sequence1);

        house.getShafts().get(0).setCabinPosition(12);
        house.getShafts().get(0).setId(0L);
        house.getShafts().get(1).setId(1L);

        Shaft shaft = house.findNearestCabin(orderSequenceOfStops);

        Assertions.assertEquals(1L, shaft.getId());
    }
}
