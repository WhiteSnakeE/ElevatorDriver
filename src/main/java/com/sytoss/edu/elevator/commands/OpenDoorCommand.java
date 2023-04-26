package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.ListIterator;

import static com.sytoss.edu.elevator.commands.CommandManager.ITERATOR_PARAM;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenDoorCommand implements Command {

    private final ShaftRepository shaftRepository;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(CommandManager.SHAFT_PARAM);
        shaft.getCabin().openDoor(shaft, (ListIterator) params.get(ITERATOR_PARAM));
        log.info("Shaft with id [{}] has [DOOR STATE]: [OPENED]", shaft.getId());

        shaftRepository.updateDoorStateById(shaft.getId(), shaft.getCabin().getDoorState());

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
