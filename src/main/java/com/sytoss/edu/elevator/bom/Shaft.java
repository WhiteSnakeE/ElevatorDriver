package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.floors.Floor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

//@Component
//@Scope("prototype")
@Slf4j
@Getter
@Setter
public class Shaft {

    private int id;
    private static int serialNumber = 0;
    private int cabinPosition;
    private ArrayList<Floor> floors;
    private Cabin cabin;
    private Engine engine;

    public Shaft () {
        id = serialNumber++;
        cabin = new Cabin();
        engine = new Engine();
    }

    public void activate () {

    }
}
