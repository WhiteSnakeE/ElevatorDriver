package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ListIterator;

import static org.mockito.Mockito.mock;

public class CabinTest {

    private final Cabin cabin = new Cabin();

    @Test
    public void openDoorTest() {
        cabin.openDoor(mock(Shaft.class), mock(ListIterator.class));
        Assertions.assertEquals(DoorState.OPENED, cabin.getDoorState());
    }

    @Test
    public void closeDoorTest() {
        cabin.closeDoor(mock(Shaft.class), mock(ListIterator.class));
        Assertions.assertEquals(DoorState.CLOSED, cabin.getDoorState());
    }

    @Test
    public void isOverWeightTest() {
        Assertions.assertFalse(cabin.isOverWeight());
    }
}
