package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShaftService {

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

    public void updateCabinPositionById(Long shaftId, int cabinPosition){
        shaftRepository.updateCabinPositionById(shaftId, cabinPosition);
    }

    public ShaftDTO getById(Long id){
        return shaftRepository.getShaftDTOById(id);
    }

    public SequenceOfStops getSequenceOfStopsByShaftId(Long id){
        ShaftDTO shaftDTO = shaftRepository.getShaftDTOById(id);
        return JsonUtil.stringJSONToSequenceOfStops(shaftDTO.getSequenceOfStops());
    }
}
