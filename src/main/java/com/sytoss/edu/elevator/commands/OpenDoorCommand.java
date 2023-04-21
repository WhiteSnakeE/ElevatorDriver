package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.OverWeightState;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenDoorCommand implements Command {

    private final ShaftRepository shaftRepository;
    private final int timeSleep = 4000;
    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(CommandManager.SHAFT_PARAM);
        shaft.getCabin().openDoor();
        log.info("Shaft with id [{}] has [DOOR STATE]: [OPENED]", shaft.getId());

        log.info("Shaft with id [{}] updated doorState in DB to: [{}]", shaft.getId(), shaft.getCabin().getDoorState());
        shaftRepository.updateDoorStateById(shaft.getId(), shaft.getCabin().getDoorState());

        while (shaft.getCabin().isOverWeight()) {
            log.info("Cabin in shaft with id [{}] is: [OVERWEIGHT]", shaft.getId());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
