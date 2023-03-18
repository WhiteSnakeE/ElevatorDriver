package com.sytoss.edu.elevator.bom.floors;

import com.sytoss.edu.elevator.bom.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.buttons.UpFloorButton;

public class MiddleFloor extends Floor implements FloorWithUpButton {
    private int id;
    private UpFloorButton upFloorButton;

    public MiddleFloor (int id) {
        this.id = id;
    }

    @Override
    public void pressUpButton () {

    }
}
