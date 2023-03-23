package com.sytoss.edu.elevator.bom.floors;

import com.sytoss.edu.elevator.bom.ButtonState;
import com.sytoss.edu.elevator.bom.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.buttons.UpFloorButton;


public class MiddleFloor extends Floor implements FloorWithUpButton {
    private UpFloorButton upFloorButton;

    @Override
    public void pressUpButton () {
        upFloorButton.setButtonState(ButtonState.PRESSED);
    }

    public MiddleFloor (int floorNumber) {
        super(floorNumber);
        upFloorButton=new UpFloorButton();
    }
}
