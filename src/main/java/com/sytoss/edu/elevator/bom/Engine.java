package com.sytoss.edu.elevator.bom;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Engine {
    private int id;
   private EngineState engineState;
    public Engine(){
        this.engineState=EngineState.STAYING;
    }
}
