package com.sytoss.edu.elevator.bom.floors;

import com.sytoss.edu.elevator.bom.ButtonState;
import com.sytoss.edu.elevator.bom.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.buttons.UpFloorButton;

public class FirstFloor extends Floor implements FloorWithUpButton {

    private final UpFloorButton upFloorButton;

    public FirstFloor (int floorNumber) {
        super(floorNumber);
        this.upFloorButton = new UpFloorButton();
    }

    @Override
    public void pressUpButton () {
        upFloorButton.setButtonState(ButtonState.PRESSED);
    }
}
