package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class MoveCabinCommand implements Command {

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        shaft.getEngine().start((Direction) params.get("Direction"));
        log.info("Engine in shaft with id [{}] has engine state: [{}]", shaft.getId(), shaft.getEngine().getEngineState());
    }
}
