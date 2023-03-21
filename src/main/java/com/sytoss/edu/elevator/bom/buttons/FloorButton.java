package com.sytoss.edu.elevator.bom.buttons;

import com.sytoss.edu.elevator.bom.ButtonState;
import com.sytoss.edu.elevator.bom.Direction;
import lombok.Getter;
import lombok.Setter;

@Setter
public abstract class FloorButton {
    private Direction direction;
    private ButtonState buttonState = ButtonState.UNPRESSED;

}
