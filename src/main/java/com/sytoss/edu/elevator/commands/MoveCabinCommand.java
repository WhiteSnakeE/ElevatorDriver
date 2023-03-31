package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class MoveCabinCommand implements Command{

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft=(Shaft) params.get("Shaft");
        shaft.getEngine().start((Direction) params.get("Direction"));

    }
}
