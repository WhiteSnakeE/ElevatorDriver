package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.OverWeightState;
import lombok.Getter;

public class Cabin extends Entity {

    @Getter
    private DoorState doorState = DoorState.CLOSED;

    @Getter
    private OverWeightState overWeightState = OverWeightState.NOT_OVERWEIGHT;

    public void openDoor() {
        this.doorState = DoorState.OPENED;
    }

    public void closeDoor() {
        this.doorState = DoorState.CLOSED;
    }

    public boolean isOverWeight() {
        return overWeightState == OverWeightState.OVERWEIGHT;
    }
}
