package com.sytoss.edu.elevator.bom.buttons;

import com.sytoss.edu.elevator.bom.ButtonState;
import com.sytoss.edu.elevator.bom.Direction;
import lombok.Setter;

@Setter
public abstract class FloorButton {
    protected Direction direction;
    protected ButtonState buttonState = ButtonState.UNPRESSED;

}
