package com.sytoss.edu.elevator.bom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
@Scope("prototype")
public class SequenceOfStops {
    private ArrayList<Integer> stopFloors;
    private int currentPosition;
}
