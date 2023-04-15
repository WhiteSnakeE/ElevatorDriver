package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class CloseDoorCommand implements Command {

    private final ShaftRepository shaftRepository;
    private final int timeSleep = 0;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        shaft.getCabin().closeDoor();
        log.info("Shaft with id [{}] has [DOOR STATE]: [CLOSED]", shaft.getId());

        log.info("Shaft with id [{}] updated doorState in DB to: [{}]", shaft.getId(), shaft.getCabin().getDoorState());
        shaftRepository.updateDoorStateById(shaft.getId(), shaft.getCabin().getDoorState().toString());

        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
