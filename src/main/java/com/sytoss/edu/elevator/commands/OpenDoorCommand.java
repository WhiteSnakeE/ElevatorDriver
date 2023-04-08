package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class OpenDoorCommand implements Command {

    private final int timeSleep=0;
    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        shaft.getCabin().openDoor();
        log.info("Shaft with id [{}] has [DOOR STATE]: [OPENED]", shaft.getId());

        while (shaft.getCabin().isOverWeight()) {
            log.info("Cabin in shaft with id [{}] is: [OVERWEIGHT]", shaft.getId());
        }
        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
