package com.sytoss.edu.elevator.listeners;

import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;

public interface ShaftListener {

    void handleCabinPositionChanged(CabinPositionChangedEvent event);
}
