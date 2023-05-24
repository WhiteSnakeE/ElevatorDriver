package com.sytoss.edu.elevator.listeners;

import com.sytoss.edu.elevator.events.OrderSequenceOfStopsChangedEvent;

public interface OrderSequenceOfStopsListener {

    void handleOrderSequenceOfStopsChanged(OrderSequenceOfStopsChangedEvent event);
}
