package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PressUpButtonCommand implements Command {

    private final ElevatorDriver elevatorDriver;

    private final CommandManager commandManager;

    @Override
    public void execute(HashMap<String, Object> params) {
        log.info("Start PressUpButton.execute COMMAND with params: {}", params);
        int numberFloor = (int) params.get(CommandManager.FLOOR_NUMBER_PARAM);
        Direction direction = (Direction) params.get(CommandManager.DIRECTION_PARAM);

        elevatorDriver.addNewSequenceToOrder(numberFloor, direction);

        commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND).execute(null);
    }
}
