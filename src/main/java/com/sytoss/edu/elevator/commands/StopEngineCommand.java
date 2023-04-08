package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class StopEngineCommand implements Command {

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        shaft.getEngine().stop();
        log.info("Shaft with id [{}] has [ENGINE STATE]: [STAYING!!]", shaft.getId());
    }
}
