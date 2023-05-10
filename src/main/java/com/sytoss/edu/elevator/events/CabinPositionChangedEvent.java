package com.sytoss.edu.elevator.events;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.Getter;

public class CabinPositionChangedEvent extends Event {
    @Getter
    private final House house;

    public CabinPositionChangedEvent(Shaft shaft, House house) {
        super(shaft);
        this.house = house;
    }
}
