package com.sytoss.edu.elevator.events;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.Getter;

public class OrderSequenceOfStopsChangedEvent extends Event {

    @Getter
    private House house;

    public OrderSequenceOfStopsChangedEvent(Shaft shaft, House house) {
        super(shaft);
        this.house = house;
    }
}
