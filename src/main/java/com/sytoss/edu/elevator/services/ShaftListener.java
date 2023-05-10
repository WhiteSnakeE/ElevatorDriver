package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;
import com.sytoss.edu.elevator.events.DoorStateChangedEvent;

public interface ShaftListener {

    void handleCabinPositionChanged(CabinPositionChangedEvent event);

    void handleDoorStateChanged(DoorStateChangedEvent event);
}
