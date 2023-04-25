package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@Slf4j
public class ElevatorDriver extends Entity {

    private List<SequenceOfStops> orderSequenceOfStops = new ArrayList<>();

    public void addNewSequenceToOrder(int floorNumber, Direction direction) {
        SequenceOfStops sequenceOfStops = new SequenceOfStops();
        sequenceOfStops.setDirection(direction);
        sequenceOfStops.setStopFloors(new ArrayList<>(List.of(floorNumber)));
        orderSequenceOfStops.add(sequenceOfStops);
    }

    public void removeSequenceFromOrder() {
        if (orderSequenceOfStops.isEmpty()) {
            throw new IllegalStateException("Order sequence of stops is empty!");
        }
        orderSequenceOfStops.remove(0);
    }
}

