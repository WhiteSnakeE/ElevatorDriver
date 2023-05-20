package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.OverWeightState;
import com.sytoss.edu.elevator.listeners.CabinListener;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cabin extends Entity {

    private DoorState doorState = DoorState.CLOSED;

    private OverWeightState overWeightState = OverWeightState.NOT_OVERWEIGHT;

    private List<CabinListener> cabinListeners = new ArrayList<>();

    public void openDoor() {
        this.doorState = DoorState.OPENED;
    }

    public void closeDoor() {
        this.doorState = DoorState.CLOSED;
    }

    public boolean isOverWeight() {
        return overWeightState.equals(OverWeightState.OVERWEIGHT);
    }

    public void addCabinListener(CabinListener cabinListener) {
        cabinListeners.add(cabinListener);
    }

    public void removeCabinListener(CabinListener cabinListener) {
        cabinListeners.remove(cabinListener);
    }
}
