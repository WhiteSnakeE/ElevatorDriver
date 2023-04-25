package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;
import com.sytoss.edu.elevator.events.DoorStateChangedEvent;
import com.sytoss.edu.elevator.services.ShaftListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@Getter
@Setter
public class Shaft extends Entity {

    private int cabinPosition;

    private SequenceOfStops sequenceOfStops;

    private Engine engine;

    private Cabin cabin;

    private List<ShaftListener> shaftListeners = new ArrayList<>();

    public boolean isCabinMoving() {
        if (sequenceOfStops != null) {
            return true;
        }
        return false;
    }

    public boolean isFree() {
        return sequenceOfStops == null;
    }

    public Shaft() {
        cabin = new Cabin();
        engine = new Engine();
        cabinPosition = 1;
    }

    public synchronized void updateSequence(ElevatorDriver elevatorDriver) {
        if (this.sequenceOfStops == null || this.sequenceOfStops.getStopFloors() == null) {
            this.sequenceOfStops = elevatorDriver.getOrderSequenceOfStops().get(0);
            elevatorDriver.removeSequenceFromOrder();
        } else {
            ArrayList<Integer> stops = new ArrayList<>(this.sequenceOfStops.getStopFloors());
            stops.addAll(elevatorDriver.getOrderSequenceOfStops().get(0).getStopFloors());
            Collections.sort(stops);
            this.sequenceOfStops.setStopFloors(stops);
            elevatorDriver.removeSequenceFromOrder();
        }
        log.info("Shaft with id {} and sequence of stops of found cabin: {}", getId(), sequenceOfStops.getStopFloors());
    }

    public void clearSequence() {
        this.sequenceOfStops = null;
    }

    public boolean isSameDirection(Direction direction, Integer currentPosition) {
        return cabinPosition <= currentPosition && direction == this.sequenceOfStops.getDirection();
    }

    public void addShaftListener(ShaftListener shaftListener) {
        shaftListeners.add(shaftListener);
    }

    public boolean removeShaftListener(ShaftListener shaftListener) {
        return shaftListeners.remove(shaftListener);
    }

    public void setCabinPosition(int currentFloor, ListIterator listIterator) {
        cabinPosition = currentFloor;

        CabinPositionChangedEvent event = new CabinPositionChangedEvent(this, listIterator);
        shaftListeners.forEach(shaftListener -> shaftListener.handleCabinPositionChanged(event));
    }

    public void openDoor(ListIterator listIterator) {
        cabin.openDoor();

        DoorStateChangedEvent event = new DoorStateChangedEvent(this, listIterator);
        shaftListeners.forEach(shaftListener -> shaftListener.handleDoorStateChanged(event));
    }

    public void closeDoor(ListIterator listIterator) {
        cabin.closeDoor();

        DoorStateChangedEvent event = new DoorStateChangedEvent(this, listIterator);
        shaftListeners.forEach(shaftListener -> shaftListener.handleDoorStateChanged(event));
    }
}
