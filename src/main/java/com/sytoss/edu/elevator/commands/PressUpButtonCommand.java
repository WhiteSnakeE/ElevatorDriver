package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class PressUpButtonCommand implements Command {

    @Autowired
    private ElevatorDriver elevatorDriver;

    @Autowired
    private CommandManager commandManager;

    @Override
    public void execute (HashMap<String, Object> params) {
        log.info("Start PressUpButton.execute COMMAND with params: {}", params);
        int numberFloor = (int) params.get("numberFloor");
        Direction direction = (Direction) params.get("Direction");
        elevatorDriver.addNewSequenceToOrder(numberFloor, direction);
        commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND).execute(null);
    }
}
