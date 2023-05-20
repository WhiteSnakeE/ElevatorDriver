package com.sytoss.edu.elevator.listeners;

import com.sytoss.edu.elevator.events.SequenceOfStopsChangedEvent;

public interface SequenceOfStopsListener {

    void handleSequenceOfStopsChanged(SequenceOfStopsChangedEvent event);
}
