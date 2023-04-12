package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CabinTest {

    private final Cabin cabin = new Cabin();

    @Test
    public void openDoorTest() {
        cabin.openDoor();
        Assertions.assertEquals(DoorState.OPENED, cabin.getDoorState());
    }

    @Test
    public void closeDoorTest() {
        cabin.closeDoor();
        Assertions.assertEquals(DoorState.CLOSED, cabin.getDoorState());
    }

    @Test
    public void isOverWeightTest() {
        Assertions.assertFalse(cabin.isOverWeight());
    }
}
