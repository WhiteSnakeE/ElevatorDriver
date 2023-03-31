package com.sytoss.edu.elevator.commands;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CommandManager {

    @Autowired
    private ObjectProvider<PressUpButtonCommand> pressUpButtonCommandProvider;
    @Autowired
    private ObjectProvider<FindNearestCabinCommand> findNearestCabinCommandProvider;
    @Autowired
    private ObjectProvider<MoveCabinCommand> moveCabinCommandObjectProvider;
    @Autowired
    private ObjectProvider<StopCabinCommand> stopDoorCommandObjectProvider;
    @Autowired
    private ObjectProvider<OpenDoorCommand> openDoorCommandObjectProvider;
    @Autowired
    private ObjectProvider<CloseDoorCommand> closeDoorCommandObjectProvider;


    private final HashMap<String, Command> commandMap = new HashMap<>();

    private Command createCommand (String nameCommand) {
        switch (nameCommand) {
            case Command.FIND_NEAREST_CABIN_COMMAND:
                return findNearestCabinCommandProvider.getObject();
            case Command.PRESS_UP_BUTTON:
                return pressUpButtonCommandProvider.getObject();
            case Command.MOVE_CABIN_COMMAND:
                return moveCabinCommandObjectProvider.getObject();
            case Command.STOP_CABIN_COMMAND:
                return stopDoorCommandObjectProvider.getObject();
            case Command.OPEN_DOOR_COMMAND:
                return openDoorCommandObjectProvider.getObject();
            case Command.CLOSE_DOOR_COMMAND:
                return closeDoorCommandObjectProvider.getObject();
            default:
                throw new IllegalArgumentException("Unknown command: " + nameCommand);
        }
    }

    public Command getCommand (String nameCommand) {
        if (!commandMap.containsKey(nameCommand)) {
            commandMap.put(nameCommand, createCommand(nameCommand));
        }
        return commandMap.get(nameCommand);
    }
}
