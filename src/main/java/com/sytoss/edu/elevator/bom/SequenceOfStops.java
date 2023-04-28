package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class SequenceOfStops extends Entity {

    private List<Integer> stopFloors;

    private Direction direction;

    public boolean isLast(int floor) {
        return floor == stopFloors.get(stopFloors.size() - 1);
    }

    public boolean isFirst(int floor) {
        return stopFloors.get(0) == floor;
    }
}
