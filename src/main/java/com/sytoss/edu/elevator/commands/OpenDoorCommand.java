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
    private final int timeSleep = 5000;
    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        shaft.getCabin().openDoor();
        log.info("Shaft with id [{}] has [DOOR STATE]: [OPENED]", shaft.getId());

        log.info("Shaft with id [{}] updated doorState in DB to: [{}]", shaft.getId(), shaft.getCabin().getDoorState());
        shaftRepository.updateDoorStateById(shaft.getId(), shaft.getCabin().getDoorState().toString());

        while (true) {
            if (shaft.getCabin().isOverWeight()) {
                log.info("Cabin in shaft with id [{}] is: [OVERWEIGHT]", shaft.getId());
                shaftRepository.updateOverweightStateById(shaft.getId(), OverWeightState.OVERWEIGHT.toString());

                try {
                    Thread.sleep(timeSleep);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                log.info("Cabin in shaft with id [{}] is: [NOT_OVERWEIGHT]", shaft.getId());
                shaftRepository.updateOverweightStateById(shaft.getId(), OverWeightState.NOT_OVERWEIGHT.toString());
                break;
            }
        }

        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
