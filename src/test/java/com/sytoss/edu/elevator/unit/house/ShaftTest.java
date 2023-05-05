package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class ShaftTest {

    private final Shaft shaft = new Shaft();

    @Test
    public void isMovingTest() {
        SequenceOfStops sequence = mock(SequenceOfStops.class);

        shaft.setSequenceOfStops(sequence);

        Assertions.assertTrue(shaft.isCabinMoving());
    }

    @Test
    public void isFreeTest() {
        Assertions.assertTrue(shaft.isFree());
    }

    @Test
    public void updateSequenceAddTest() {
        ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(List.of(5));

        when(elevatorDriver.getOrderSequenceOfStops()).thenReturn(List.of(sequence));

        shaft.updateSequence(elevatorDriver);
        Assertions.assertEquals(5, shaft.getSequenceOfStops().getStopFloors().get(0));
    }

    @Test
    public void updateSequenceMergeTest() {
        shaft.clearSequence();
        ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

        SequenceOfStops sequenceOfStops1 = new SequenceOfStops();
        sequenceOfStops1.setStopFloors(List.of(5));
        shaft.setSequenceOfStops(sequenceOfStops1);

        SequenceOfStops sequenceOfStops2 = new SequenceOfStops();
        sequenceOfStops2.setStopFloors(List.of(3));

        when(elevatorDriver.getOrderSequenceOfStops()).thenReturn(List.of(sequenceOfStops2));

        shaft.updateSequence(elevatorDriver);
        Assertions.assertEquals(List.of(3, 5), shaft.getSequenceOfStops().getStopFloors());
    }

    @Test
    public void isSameDirectionTest() {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.UPWARDS);

        shaft.setCabinPosition(1);
        shaft.setSequenceOfStops(sequence);

        Assertions.assertTrue(shaft.isSameDirection(Direction.UPWARDS, 5));

        shaft.setCabinPosition(6);
        Assertions.assertFalse(shaft.isSameDirection(Direction.UPWARDS, 5));
    }

    @Test
    public void openCabinDoorTest() {
        Cabin cabin = mock(Cabin.class);
        shaft.setCabin(cabin);

        shaft.openCabinDoor();

        verify(cabin).openDoor();
    }

    @Test
    public void closeCabinDoorTest() {
        Cabin cabin = mock(Cabin.class);
        shaft.setCabin(cabin);

        shaft.closeCabinDoor();

        verify(cabin).closeDoor();
    }
}
