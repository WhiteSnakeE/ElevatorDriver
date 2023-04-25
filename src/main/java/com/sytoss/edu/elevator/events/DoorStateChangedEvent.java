package com.sytoss.edu.elevator.events;

import com.sytoss.edu.elevator.bom.Shaft;

import java.util.ListIterator;

public class DoorStateChangedEvent extends Event {

    public DoorStateChangedEvent(Shaft shaft, ListIterator listIterator) {
        super(shaft, listIterator);
    }
}
