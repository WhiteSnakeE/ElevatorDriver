package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.commands.CommandManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
        engine = new Engine();
        cabinPosition = 1;
    }

    public void updateSequence( List<SequenceOfStops> orderSequenceOfStops){
        if (this.sequenceOfStops == null) {
            this.sequenceOfStops=orderSequenceOfStops.get(0);
        } else {
            ArrayList<Integer> stops = new ArrayList<>(this.sequenceOfStops.getStopFloors());
            stops.addAll(orderSequenceOfStops.get(0).getStopFloors());
            Collections.sort(stops);
            this.sequenceOfStops.setStopFloors(stops);
        }
    }
    public void clearSequence(){}

    public boolean isSameDirection (Direction direction, Integer currentPosition) {
        return cabinPosition <= currentPosition && direction == this.sequenceOfStops.getDirection();
    }

}
