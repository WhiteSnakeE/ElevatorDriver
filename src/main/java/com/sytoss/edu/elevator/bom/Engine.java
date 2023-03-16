package com.sytoss.edu.elevator.bom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Engine {
    private int id;
    private EngineState engineState;
    public Engine(){
        this.engineState=EngineState.STAYING;
    }
}
