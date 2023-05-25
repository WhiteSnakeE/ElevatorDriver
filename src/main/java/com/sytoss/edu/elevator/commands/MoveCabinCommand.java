package com.sytoss.edu.elevator.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.sytoss.edu.elevator.HouseThreadPool.VISIT_FLOOR_TIME_SLEEP;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoveCabinCommand implements Command {

    private final CommandManager commandManager;

    @Override
    public void execute(HashMap<String, Object> params) {
        commandManager.getCommand(START_ENGINE_COMMAND).execute(params);
        commandManager.scheduleCommand(VISIT_FLOOR_COMMAND, params, VISIT_FLOOR_TIME_SLEEP);
    }
}

