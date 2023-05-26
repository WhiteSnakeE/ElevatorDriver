package com.sytoss.edu.elevator.events;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.Getter;

public class OrderSequenceOfStopsChangedEvent extends Event {

    @Getter
    private final House house;

    public OrderSequenceOfStopsChangedEvent(House house) {
        super(null);
        this.house = house;
    }
}
