package com.sytoss.edu.elevator.bom.buttons;

import com.sytoss.edu.elevator.bom.ButtonState;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.Entity;
import lombok.Setter;

@Setter
public abstract class FloorButton extends Entity {
    protected Direction direction;
    protected ButtonState buttonState = ButtonState.UNPRESSED;

}
