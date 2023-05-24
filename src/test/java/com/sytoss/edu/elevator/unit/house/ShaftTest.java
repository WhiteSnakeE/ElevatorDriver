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

    @Test
    public void isMovingTest() {
        Shaft shaft = new Shaft();
        SequenceOfStops sequence = mock(SequenceOfStops.class);

        shaft.setSequenceOfStops(sequence);

        Assertions.assertTrue(shaft.isCabinMoving());
    }

    @Test
    public void isFreeTest() {
        Shaft shaft = new Shaft();
        Assertions.assertTrue(shaft.isFree());
    }

    @Test
    public void updateSequenceAddTest() {
        Shaft shaft = new Shaft();
        ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(List.of(5));

        when(elevatorDriver.getOrderSequenceOfStops()).thenReturn(List.of(sequence));

        shaft.updateSequence(elevatorDriver.getOrderSequenceOfStops());
        Assertions.assertEquals(5, shaft.getSequenceOfStops().getStopFloors().get(0));
    }

    @Test
    public void updateSequenceMergeTest() {
        Shaft shaft = new Shaft();
        ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

        SequenceOfStops sequenceOfStops1 = new SequenceOfStops();
        sequenceOfStops1.setStopFloors(List.of(5));
        shaft.setSequenceOfStops(sequenceOfStops1);

        SequenceOfStops sequenceOfStops2 = new SequenceOfStops();
        sequenceOfStops2.setStopFloors(List.of(3));

        when(elevatorDriver.getOrderSequenceOfStops()).thenReturn(List.of(sequenceOfStops2));

        shaft.updateSequence(elevatorDriver.getOrderSequenceOfStops());
        Assertions.assertEquals(List.of(3, 5), shaft.getSequenceOfStops().getStopFloors());
    }

    @Test
    public void isSameDirectionTest() {
        Shaft shaft = new Shaft();
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
        Shaft shaft = new Shaft();
        Cabin cabin = mock(Cabin.class);
        shaft.setCabin(cabin);

        shaft.openCabinDoor();

        verify(cabin).openDoor();
    }

    @Test
    public void closeCabinDoorTest() {
        Shaft shaft = new Shaft();
        Cabin cabin = mock(Cabin.class);
        shaft.setCabin(cabin);

        shaft.closeCabinDoor();

        verify(cabin).closeDoor();
    }
}
