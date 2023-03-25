package com.sytoss.edu.elevator.commands;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CommandManager {
    public static final String FIND_NEAREST_CABIN_COMMAND = "FindNearestCabinCommand";

    public static final String PRESS_UP_BUTTON = "PressUpButton";

    @Autowired
    private ObjectProvider<PressUpButtonCommand> pressUpButtonCommandProvider;

    @Autowired
    private ObjectProvider<FindNearestCabinCommand> findNearestCabinCommandProvider;

    private final HashMap<String, Command> commandMap = new HashMap<>();

    private Command createCommand (String nameCommand) {
        switch (nameCommand) {
            case FIND_NEAREST_CABIN_COMMAND:
                return findNearestCabinCommandProvider.getObject();
            case PRESS_UP_BUTTON:
                return pressUpButtonCommandProvider.getObject();
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
