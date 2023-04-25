package com.sytoss.edu.elevator.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CommandManager {

    private final ObjectProvider<PressUpButtonCommand> pressUpButtonCommandProvider;
    private final ObjectProvider<FindNearestCabinCommand> findNearestCabinCommandProvider;
    private final ObjectProvider<MoveCabinCommand> moveCabinCommandObjectProvider;
    private final ObjectProvider<StartEngineCommand> startEngineCommandObjectProvider;
    private final ObjectProvider<StopEngineCommand> stopEngineCommandObjectProvider;
    private final ObjectProvider<OpenDoorCommand> openDoorCommandObjectProvider;
    private final ObjectProvider<CloseDoorCommand> closeDoorCommandObjectProvider;
    public static final String SHAFT_PARAM = "Shaft";
    public static final String DIRECTION_PARAM = "Direction";
    public static final String FLOOR_NUMBER_PARAM = "numberFloor";
    public static final String FLOORS_PARAM = "Floors";
    public static final String ITERATOR_PARAM = "Iterator";
    private final HashMap<String, Command> commandMap = new HashMap<>();

    private Command createCommand (String nameCommand) {
        return switch (nameCommand) {
            case Command.PRESS_UP_BUTTON -> pressUpButtonCommandProvider.getObject();
            case Command.FIND_NEAREST_CABIN_COMMAND -> findNearestCabinCommandProvider.getObject();
            case Command.MOVE_CABIN_COMMAND -> moveCabinCommandObjectProvider.getObject();
            case Command.START_ENGINE_COMMAND -> startEngineCommandObjectProvider.getObject();
            case Command.STOP_ENGINE_COMMAND -> stopEngineCommandObjectProvider.getObject();
            case Command.OPEN_DOOR_COMMAND -> openDoorCommandObjectProvider.getObject();
            case Command.CLOSE_DOOR_COMMAND -> closeDoorCommandObjectProvider.getObject();
            default -> throw new IllegalArgumentException("Unknown command: " + nameCommand);
        };
    }

    public Command getCommand (String nameCommand) {
        if (!commandMap.containsKey(nameCommand)) {
            commandMap.put(nameCommand, createCommand(nameCommand));
        }
        return commandMap.get(nameCommand);
    }
}
