package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class CloseDoorCommand implements Command{
    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft=(Shaft) params.get("Shaft");
        shaft.getCabin().closeDoor();
        log.info("Door cabin with id {} was closed",shaft.getCabin().getId());
    }
}
