package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@Getter
@Setter
public class Shaft extends Entity {

    private int cabinPosition;
    private SequenceOfStops sequenceOfStops;
    private Engine engine;
    private Cabin cabin;

    public boolean isFree () {
        return sequenceOfStops == null;
    }

    public Shaft () {
        cabin = new Cabin();
        engine = new Engine();
        cabinPosition = 1;
    }

    public synchronized boolean updateSequence (ElevatorDriver elevatorDriver) {
        boolean isNeedActivate = false;
        if (this.sequenceOfStops == null || this.sequenceOfStops.getStopFloors() == null) {
            this.sequenceOfStops = elevatorDriver.getOrderSequenceOfStops().get(0);
            elevatorDriver.removeSequenceFromOrder();
            isNeedActivate = true;
        } else {
            ArrayList<Integer> stops = new ArrayList<>(this.sequenceOfStops.getStopFloors());
            stops.addAll(elevatorDriver.getOrderSequenceOfStops().get(0).getStopFloors());
            Collections.sort(stops);
            this.sequenceOfStops.setStopFloors(stops);
            elevatorDriver.removeSequenceFromOrder();
        }
        log.info("Shaft with id {} and sequence of stops of found cabin: {}",getId() ,sequenceOfStops.getStopFloors());
        return isNeedActivate;
    }

    public void clearSequence () {
        this.sequenceOfStops = null;
    }

    public boolean isSameDirection (Direction direction, Integer currentPosition) {
        return cabinPosition <= currentPosition && direction == this.sequenceOfStops.getDirection();
    }
}
