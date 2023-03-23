package com.sytoss.edu.elevator.bom;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class Shaft extends Entity {

    private int cabinPosition;
    private SequenceOfStops sequenceOfStops;
    private Cabin cabin;
    private Engine engine;

    public Shaft () {
        cabin = new Cabin();
        engine = new Engine();
        cabinPosition = 1;
    }

}
