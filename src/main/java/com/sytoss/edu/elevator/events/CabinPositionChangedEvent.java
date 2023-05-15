package com.sytoss.edu.elevator.events;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.Getter;

public class CabinPositionChangedEvent extends Event {

    public CabinPositionChangedEvent(Shaft shaft) {
        super(shaft);
    }
}
