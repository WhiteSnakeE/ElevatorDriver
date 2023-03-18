package com.sytoss.edu.elevator.bom;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@Setter
public class Shaft {

    private int id;
    private static int serialNumber = 0;
    private int cabinPosition;
    private SequenceOfStops sequenceOfStops;
    private Cabin cabin;
    private Engine engine;

    public Shaft () {
        id = serialNumber++;
        cabin = new Cabin();
        engine = new Engine();
    }

    public void activate () {
    }

    public void updateSequence (SequenceOfStops sequenceOfStops) {
        this.sequenceOfStops=sequenceOfStops;
    }
}
