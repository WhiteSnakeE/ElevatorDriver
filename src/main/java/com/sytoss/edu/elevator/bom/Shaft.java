package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;
import com.sytoss.edu.elevator.events.DoorStateChangedEvent;
import com.sytoss.edu.elevator.events.EngineStateChangedEvent;
import com.sytoss.edu.elevator.events.SequenceOfStopsChangedEvent;
import com.sytoss.edu.elevator.listeners.SequenceOfStopsListener;
import com.sytoss.edu.elevator.listeners.ShaftListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Setter
public class Shaft extends Entity {

    private int cabinPosition;

    private SequenceOfStops sequenceOfStops;

    private Engine engine;

    private Cabin cabin;

    private List<ShaftListener> shaftListeners = new ArrayList<>();

    private List<SequenceOfStopsListener> sequenceOfStopsListeners = new ArrayList<>();

    public Shaft() {
        cabin = new Cabin();
        engine = new Engine();
        cabinPosition = 1;
    }

    public boolean isCabinMoving() {
        return !isFree();
    }

    public boolean isFree() {
        return sequenceOfStops == null;
    }

    public synchronized void updateSequence(ElevatorDriver elevatorDriver) {
        if (isFree()) {
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
        fireSequenceOfStops();
    }

    public void clearSequence() {
        this.sequenceOfStops = null;
        fireSequenceOfStops();
    }

    public boolean isSameDirection(Direction direction, Integer currentPosition) {
        return cabinPosition <= currentPosition && direction.equals(this.sequenceOfStops.getDirection());
    }

    public void addShaftListener(ShaftListener shaftListener) {
        shaftListeners.add(shaftListener);
    }

    public boolean removeShaftListener(ShaftListener shaftListener) {
        return shaftListeners.remove(shaftListener);
    }

    public void addSequenceOfStopsListener(SequenceOfStopsListener sequenceOfStopsListener) {
        sequenceOfStopsListeners.add(sequenceOfStopsListener);
    }

    public boolean removeSequenceOfStopsListener(SequenceOfStopsListener sequenceOfStopsListener) {
        return sequenceOfStopsListeners.remove(sequenceOfStopsListener);
    }

    public void setCabinPosition(int currentFloor) {
        cabinPosition = currentFloor;
        fireCabinPosition();
    }

    public void openCabinDoor() {
        cabin.openDoor();
        fireDoorState();
    }

    public void closeCabinDoor() {
        cabin.closeDoor();
        fireDoorState();
    }

    public void startEngine(Direction direction) {
        engine.start(direction);
        fireEngineState();
    }

    public void stopEngine() {
        engine.stop();
        fireEngineState();
    }

    private void fireDoorState() {
        DoorStateChangedEvent event = new DoorStateChangedEvent(this);
        cabin.getCabinListeners().forEach(cabinListener -> cabinListener.handleDoorStateChanged(event));
    }

    private void fireCabinPosition() {
        CabinPositionChangedEvent event = new CabinPositionChangedEvent(this);
        shaftListeners.forEach(shaftListener -> shaftListener.handleCabinPositionChanged(event));
    }

    private void fireEngineState() {
        EngineStateChangedEvent event = new EngineStateChangedEvent(this);
        engine.getEngineListeners().forEach(engineListener -> engineListener.handleEngineStateChanged(event));
    }

    private void fireSequenceOfStops() {
        SequenceOfStopsChangedEvent event = new SequenceOfStopsChangedEvent(this);
        sequenceOfStopsListeners.forEach(sequenceOfStopsListener -> sequenceOfStopsListener.handleSequenceOfStopsChanged(event));
    }
}
