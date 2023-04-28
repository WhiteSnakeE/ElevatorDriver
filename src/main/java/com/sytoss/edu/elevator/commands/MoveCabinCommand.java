package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.sytoss.edu.elevator.commands.CommandManager.DIRECTION_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static com.sytoss.edu.elevator.HouseThreadPool.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoveCabinCommand implements Command {

    private final CommandManager commandManager;
    private final HouseThreadPool houseThreadPool;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(SHAFT_PARAM);
        params.put(CommandManager.DIRECTION_PARAM, shaft.getSequenceOfStops().getDirection());

        HashMap<String, Object> paramsActivateCommand = new HashMap<>();

        paramsActivateCommand.put(SHAFT_PARAM, shaft);
        paramsActivateCommand.put(DIRECTION_PARAM, Direction.UPWARDS);

        commandManager.getCommand(START_ENGINE_COMMAND).execute(paramsActivateCommand);
        houseThreadPool.getFixedThreadPool().schedule(() -> commandManager.getCommand(VISIT_FLOOR_COMMAND).execute(paramsActivateCommand), VISIT_FLOOR_TIME_SLEEP, TimeUnit.MILLISECONDS);
    }
}

