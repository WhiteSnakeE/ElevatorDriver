package com.sytoss.edu.elevator.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CommandManager {

    private final ObjectProvider<PressUpButtonCommand> pressUpButtonCommandProvider;
    private final ObjectProvider<FindNearestCabinCommand> findNearestCabinCommandProvider;
    private final ObjectProvider<ActivateShaftCommand> activateShaftCommandProvider;
    private final ObjectProvider<MoveCabinCommand> moveCabinCommandObjectProvider;
    private final ObjectProvider<StopCabinCommand> stopDoorCommandObjectProvider;
    private final ObjectProvider<OpenDoorCommand> openDoorCommandObjectProvider;
    private final ObjectProvider<CloseDoorCommand> closeDoorCommandObjectProvider;

    private final HashMap<String, Command> commandMap = new HashMap<>();

    private Command createCommand (String nameCommand) {
        switch (nameCommand) {
            case Command.PRESS_UP_BUTTON:
                return pressUpButtonCommandProvider.getObject();
            case Command.FIND_NEAREST_CABIN_COMMAND:
                return findNearestCabinCommandProvider.getObject();
            case Command.ACTIVATE_SHAFT_COMMAND:
                return activateShaftCommandProvider.getObject();
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
