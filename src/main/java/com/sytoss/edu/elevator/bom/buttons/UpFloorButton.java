package com.sytoss.edu.elevator.bom.buttons;

import com.sytoss.edu.elevator.bom.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpFloorButton extends FloorButton {
    private int id;
    public UpFloorButton(){
        setDirection(Direction.UPWARDS);
    }
}
