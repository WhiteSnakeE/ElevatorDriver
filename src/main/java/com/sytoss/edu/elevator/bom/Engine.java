package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class Engine extends Entity {

    @Getter
    private EngineState engineState = EngineState.STAYING;

    public void start (Direction direction) {
        switch (direction){
            case UPWARDS ->engineState=EngineState.GOING_UP;
            case DOWNWARDS ->engineState=EngineState.GOING_DOWN;
            default -> stop();
        }
    }

    public void stop () {
        this.engineState = EngineState.STAYING;
    }
}
