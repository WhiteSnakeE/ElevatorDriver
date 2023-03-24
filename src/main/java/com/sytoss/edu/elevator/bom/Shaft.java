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
    private Engine engine;

    public Shaft () {
        engine = new Engine();
        cabinPosition = 1;
    }

}
