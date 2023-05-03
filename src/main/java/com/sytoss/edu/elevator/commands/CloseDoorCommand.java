package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class CloseDoorCommand implements Command {

    private final ShaftRepository shaftRepository;

    @Override
    public void execute(HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(CommandManager.SHAFT_PARAM);
        log.info("Shaft with id [{}] has [DOOR STATE]: [CLOSED]", shaft.getId());
        shaft.closeCabinDoor();
        shaftRepository.updateDoorStateById(shaft.getId(), shaft.getCabin().getDoorState());
        shaftRepository.updateSequenceById(shaft.getId(), JsonUtil.sequenceToStringInJSON(shaft.getSequenceOfStops()));
    }
}
