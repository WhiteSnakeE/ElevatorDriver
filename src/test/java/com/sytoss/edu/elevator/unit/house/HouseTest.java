package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CommandManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HouseTest {

    private final HouseBuilder houseBuilder = new HouseBuilder(mock(CommandManager.class));

    private final House house = houseBuilder.build(2, 16);

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    @Test
    public void moveSequenceToShaftTest () {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.UPWARDS);
        sequence.setStopFloors(List.of(7));

        when(elevatorDriver.getOrderSequenceOfStops()).thenReturn(List.of(sequence));
        Shaft shaft = house.moveSequenceToShaft(elevatorDriver);

        Assertions.assertEquals(7, shaft.getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.UPWARDS, shaft.getSequenceOfStops().getDirection());
        Assertions.assertNull(house.getShafts().get(1).getSequenceOfStops());
    }
}
