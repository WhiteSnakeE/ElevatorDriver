package com.sytoss.edu.elevator.events;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.Getter;

@Getter
public abstract class Event {

    private final Shaft shaft;


    public Event( Shaft shaft) {
        this.shaft = shaft;

    }
}
