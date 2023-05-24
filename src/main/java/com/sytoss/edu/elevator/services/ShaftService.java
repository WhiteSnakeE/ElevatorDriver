package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;
import com.sytoss.edu.elevator.events.SequenceOfStopsChangedEvent;
import com.sytoss.edu.elevator.listeners.SequenceOfStopsListener;
import com.sytoss.edu.elevator.listeners.ShaftListener;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ShaftService implements ShaftListener, SequenceOfStopsListener {

    private final ShaftRepository shaftRepository;

    public void updateDoorStateById(Long shaftId, DoorState doorState) {
        shaftRepository.updateDoorStateById(shaftId, doorState);
    }

    public void updateSequenceById(Long shaftId, SequenceOfStops sequence) {
        shaftRepository.updateSequenceById(shaftId, JsonUtil.sequenceToStringInJSON(sequence));
    }

    public void updateEngineStateById(Long shaftId, EngineState engineState) {
        shaftRepository.updateEngineStateById(shaftId, engineState);
    }

    public void updateCabinPositionById(Long shaftId, int cabinPosition) {
        shaftRepository.updateCabinPositionById(shaftId, cabinPosition);
    }

    public SequenceOfStops getSequenceOfStopsByShaftId(Long id) {
        ShaftDTO shaftDTO = shaftRepository.getShaftDTOById(id);
        return JsonUtil.stringJSONToSequenceOfStops(shaftDTO.getSequenceOfStops());
    }

    @Override
    public void handleCabinPositionChanged(CabinPositionChangedEvent event) {
        shaftRepository.updateCabinPositionById(event.getShaft().getId(), event.getShaft().getCabinPosition());
        log.info("CabinPosition was updated to [{}] in Shaft with id [{}]", event.getShaft().getCabinPosition(), event.getShaft().getId());
    }

    @Override
    public void handleSequenceOfStopsChanged(SequenceOfStopsChangedEvent event) {
        Shaft shaft = event.getShaft();
        if (shaft.getSequenceOfStops().isLast(shaft.getCabinPosition())) {
            shaftRepository.updateSequenceById(shaft.getId(), JsonUtil.sequenceToStringInJSON(null));
        } else {
            shaftRepository.updateSequenceById(shaft.getId(), JsonUtil.sequenceToStringInJSON(shaft.getSequenceOfStops()));
        }
        log.info("SequenceOfStops was updated in Shaft with id [{}] in DB", shaft.getId());
    }
}
