package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.events.EngineStateChangedEvent;
import com.sytoss.edu.elevator.listeners.EngineListener;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EngineService implements EngineListener {

    private final ShaftRepository shaftRepository;

    @Override
    public void handleEngineStateChanged(EngineStateChangedEvent event) {
        shaftRepository.updateEngineStateById(event.getShaft().getId(), event.getShaft().getEngine().getEngineState());
        log.info("EngineState was updated to [{}] in shaft with id [{}]", event.getShaft().getEngine().getEngineState(), event.getShaft().getId());
    }
}
