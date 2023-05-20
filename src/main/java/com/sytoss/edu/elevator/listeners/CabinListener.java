package com.sytoss.edu.elevator.listeners;

import com.sytoss.edu.elevator.events.DoorStateChangedEvent;

public interface CabinListener {

    void handleDoorStateChanged(DoorStateChangedEvent event);
}
