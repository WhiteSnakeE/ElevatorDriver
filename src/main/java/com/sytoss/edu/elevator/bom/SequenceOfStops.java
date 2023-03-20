package com.sytoss.edu.elevator.bom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class SequenceOfStops {
    private int id;
    private ArrayList<Integer> stopFloors;
    private int currentFloor;
    private Direction direction;

    public SequenceOfStops () {
        stopFloors = new ArrayList<>();
    }
}
