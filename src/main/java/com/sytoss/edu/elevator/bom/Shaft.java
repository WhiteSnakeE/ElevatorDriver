package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter
@Setter
public class Shaft extends Entity {

    private int cabinPosition;

    private SequenceOfStops sequenceOfStops;

    private Engine engine;

    private Cabin cabin;

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
}
