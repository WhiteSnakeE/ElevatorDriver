package com.sytoss.edu.elevator.bom.floors;

import com.sytoss.edu.elevator.bom.ButtonState;
import com.sytoss.edu.elevator.bom.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.buttons.UpFloorButton;
import com.sytoss.edu.elevator.bom.floors.Floor;

public class FirstFloor extends Floor implements FloorWithUpButton {
    private int id;

    private final UpFloorButton upFloorButton = new UpFloorButton();

    public FirstFloor (int id) {
        this.id=id;
    }

    @Override
    public void pressUpButton () {
        upFloorButton.isPressed(true);
        upFloorButton.setButtonState(ButtonState.PRESSED);
    }
}
