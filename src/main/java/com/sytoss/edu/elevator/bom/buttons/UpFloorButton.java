package com.sytoss.edu.elevator.bom.buttons;

import com.sytoss.edu.elevator.bom.Direction;
import lombok.Setter;

@Setter
public class UpFloorButton extends FloorButton {

    public UpFloorButton () {
        this.direction=Direction.UPWARDS;
    }
}
