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
public class StopEngineCommand implements Command {

    private final ShaftRepository shaftRepository;
    private int timeSleep = 10000;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        shaft.getEngine().stop();
        log.info("Shaft with id [{}] has [ENGINE STATE]: [STAYING!!]", shaft.getId());

        log.info("Shaft with id [{}] updated engineState in DB to: [{}]", shaft.getId(), shaft.getEngine().getEngineState());
        shaftRepository.updateEngineStateById(shaft.getId(), shaft.getEngine().getEngineState().toString());

        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
