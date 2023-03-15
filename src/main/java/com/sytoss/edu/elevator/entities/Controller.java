package com.sytoss.edu.elevator.entities;

import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.interfaces.Command;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Controller {

    public Controller() {
        registerCommand("findNearestCabin", new FindNearestCabinCommand(this));
    }

    private final HashMap<String, Command> commandMap = new HashMap<>();

    public void registerCommand (String nameCommand, Command command) {
        commandMap.put(nameCommand, command);
    }

    public void execute (String commandName, HashMap<String, String> params) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new IllegalStateException("no command registered for " + commandName);
        }
        command.execute(params);
    }

    public void findNearestCabin (int floorInt) {
        System.out.println("Floor number " + floorInt);
    }
}

