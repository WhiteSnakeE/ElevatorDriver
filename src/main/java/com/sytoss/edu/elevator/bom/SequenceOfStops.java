package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class SequenceOfStops extends Entity {
    private ArrayList<Integer> stopFloors;
    //private int currentFloor;
    private Direction direction;
}
