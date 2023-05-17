package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.services.ShaftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenDoorCommand implements Command {

    private final ShaftService shaftService;

    @Override
    public void execute(HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(CommandManager.SHAFT_PARAM);
        log.info("Shaft with id [{}] has [DOOR STATE]: [OPENED]", shaft.getId());
        shaft.openCabinDoor();

        shaftService.updateDoorStateById(shaft.getId(), shaft.getCabin().getDoorState());

        while (shaft.getCabin().isOverWeight()) {
            log.info("Cabin in shaft with id [{}] is: [OVERWEIGHT]", shaft.getId());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
