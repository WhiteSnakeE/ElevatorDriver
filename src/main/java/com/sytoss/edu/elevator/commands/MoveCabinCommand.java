package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.sytoss.edu.elevator.HouseThreadPool.VISIT_FLOOR_TIME_SLEEP;
import static com.sytoss.edu.elevator.commands.CommandManager.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoveCabinCommand implements Command {

    private final CommandManager commandManager;

    @Override
    public void execute(HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(SHAFT_PARAM);
        params.put(CommandManager.DIRECTION_PARAM, shaft.getSequenceOfStops().getDirection());

        commandManager.getCommand(START_ENGINE_COMMAND).execute(params);

        commandManager.scheduleCommand(VISIT_FLOOR_COMMAND, params, VISIT_FLOOR_TIME_SLEEP);
    }
}

