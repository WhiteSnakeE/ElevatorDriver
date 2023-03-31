package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import lombok.Setter;

@Setter
public class Engine extends Entity {

    private EngineState engineState=EngineState.STAYING;
    public void start(Direction direction){
        if(direction==Direction.UPWARDS){
            engineState=EngineState.GOING_UP;
        }
        else{
            engineState=EngineState.GOING_DOWN;
        }
    }
    public void stop(){
        this.engineState=EngineState.STAYING;
    }
}
