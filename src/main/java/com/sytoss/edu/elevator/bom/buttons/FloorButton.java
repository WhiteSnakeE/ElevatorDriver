package com.sytoss.edu.elevator.bom.buttons;

import com.sytoss.edu.elevator.bom.ButtonState;
import com.sytoss.edu.elevator.bom.Direction;

public abstract class FloorButton {
    private Direction direction;
    private ButtonState buttonState = ButtonState.UNPRESSED;
    public void isPressed(boolean state) {}
}
