package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.events.DoorStateChangedEvent;
import com.sytoss.edu.elevator.listeners.CabinListener;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CabinService implements CabinListener {

    private final ShaftRepository shaftRepository;

    @Override
    public void handleDoorStateChanged(DoorStateChangedEvent event) {
        shaftRepository.updateDoorStateById(event.getShaft().getId(), event.getShaft().getCabin().getDoorState());
        log.info("DoorState was updated to [{}] in Shaft with id [{}]", event.getShaft().getCabin().getDoorState(), event.getShaft().getId());
    }
}
