package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.OverWeightState;
import com.sytoss.edu.elevator.events.DoorStateChangedEvent;
import com.sytoss.edu.elevator.services.ShaftListener;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Getter
@Setter
public class Cabin extends Entity {

    private DoorState doorState = DoorState.CLOSED;

    private OverWeightState overWeightState = OverWeightState.NOT_OVERWEIGHT;

    public void openDoor(Shaft shaft, ListIterator listIterator) {
        this.doorState = DoorState.OPENED;
        fireOpenDoor(shaft, listIterator);
    }

    public void closeDoor(Shaft shaft, ListIterator listIterator) {
        this.doorState = DoorState.CLOSED;
        fireCloseDoor(shaft, listIterator);
    }

    public boolean isOverWeight() {
        return overWeightState == OverWeightState.OVERWEIGHT;
    }

    private void fireOpenDoor(Shaft shaft, ListIterator listIterator) {
        DoorStateChangedEvent event = new DoorStateChangedEvent(shaft, listIterator);
        shaft.getShaftListeners().forEach(shaftListener -> shaftListener.handleDoorStateChanged(event));
    }

    private void fireCloseDoor(Shaft shaft, ListIterator listIterator) {
        DoorStateChangedEvent event = new DoorStateChangedEvent(shaft, listIterator);
        shaft.getShaftListeners().forEach(shaftListener -> shaftListener.handleDoorStateChanged(event));
    }
}
