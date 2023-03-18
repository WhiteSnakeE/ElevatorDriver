package com.sytoss.edu.elevator.bom;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SequenceOfStops {
    private int id;
    private ArrayList<Integer> stopFloors;
    private int currentFloor;
    private Direction direction;
    public SequenceOfStops(){
        stopFloors=new ArrayList<>();
    }
}
