package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.DoorState;

public class Cabin extends Entity {

    private DoorState doorState=DoorState.CLOSED;
    public void openDoor(){
        this.doorState=DoorState.OPENED;
    }
    public void closeDoor(){
        this.doorState=DoorState.CLOSED;
    }
    public boolean isOverWeight(){return false;}
}
