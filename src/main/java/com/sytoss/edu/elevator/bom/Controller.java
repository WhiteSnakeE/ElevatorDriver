package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class Controller {
    private final HashMap<String, Command> commandMap = new HashMap<>();
    private final ArrayList<SequenceOfStops> orderSequenceOfStops = new ArrayList<>();
    private final ArrayList<Shaft> shafts = new ArrayList<>();

    @PostConstruct
    public void myInit () {
        shafts.add(new Shaft());
        shafts.add(new Shaft());
        registerCommand("findNearestCabin", new FindNearestCabinCommand(this));
    }

    public void registerCommand (String nameCommand, Command command) {
        commandMap.put(nameCommand, command);
    }

    public void runCommands (String commandName, HashMap<String, String> params) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new IllegalStateException("no command registered for " + commandName);
        }
        command.execute(params);
    }

    public void findNearestCabin (int floorInt) {
        shafts.get(0).setCabinPosition(3);
        shafts.get(1).setCabinPosition(4);
        Shaft shaftResult=null;
        int min = Integer.MAX_VALUE;
        for (Shaft shaft : shafts) {
            if (Math.abs(floorInt - shaft.getCabinPosition()) < min) {
                min = Math.abs(floorInt - shaft.getCabinPosition());
                shaftResult=shaft;
            }
        }
        System.out.println("Floor number " +shaftResult.getId());

    }
}

