package com.sytoss.edu.elevator.events;

import com.sytoss.edu.elevator.bom.Shaft;
import lombok.Getter;

import java.util.ListIterator;

@Getter
public abstract class Event {

    private final Shaft shaft;

    private final ListIterator listIterator;

    public Event(Shaft shaft, ListIterator listIterator) {
        this.shaft = shaft;
        this.listIterator = listIterator;
    }
}
