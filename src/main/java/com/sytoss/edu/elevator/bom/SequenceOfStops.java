package com.sytoss.edu.elevator.bom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.listeners.SequenceOfStopsListener;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SequenceOfStops extends Entity {

    private List<Integer> stopFloors;

    private Direction direction;

    @JsonIgnore
    private List<SequenceOfStopsListener> sequenceOfStopsListeners = new ArrayList<>();

    public boolean isLast(int floor) {
        return floor == stopFloors.get(stopFloors.size() - 1);
    }

    public boolean isFirst(int floor) {
        return stopFloors.get(0) == floor;
    }

    public void addSequenceOfStopsListener(SequenceOfStopsListener sequenceOfStopsListener) {
        sequenceOfStopsListeners.add(sequenceOfStopsListener);
    }
}
