package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class Engine extends Entity {

    private EngineState engineState = EngineState.STAYING;

    public void start (Direction direction) {
        if (engineState == EngineState.GOING_UP || engineState == EngineState.GOING_DOWN) {
            return;
        }
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
