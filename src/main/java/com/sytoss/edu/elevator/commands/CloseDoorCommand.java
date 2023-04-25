package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import static com.sytoss.edu.elevator.commands.CommandManager.ITERATOR_PARAM;

@Component
@Slf4j
@RequiredArgsConstructor
public class CloseDoorCommand implements Command {

    private final ShaftRepository shaftRepository;

    private final HouseThreadPool houseThreadPool;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(CommandManager.SHAFT_PARAM);

        houseThreadPool.getFixedThreadPool().schedule(() -> shaft.closeDoor((ListIterator) params.get(ITERATOR_PARAM)), 2000, TimeUnit.MILLISECONDS);

        try {
            houseThreadPool.getFixedThreadPool().awaitTermination(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Shaft with id [{}] has [DOOR STATE]: [CLOSED]", shaft.getId());

        log.info("Shaft with id [{}] updated doorState in DB to: [{}]", shaft.getId(), shaft.getCabin().getDoorState());
        shaftRepository.updateDoorStateById(shaft.getId(), shaft.getCabin().getDoorState());
    }
}
