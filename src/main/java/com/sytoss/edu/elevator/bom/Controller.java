package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.bom.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.floors.Floor;
import com.sytoss.edu.elevator.bom.floors.MiddleFloor;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@Getter
@Setter
public class Controller {
    private int id;
    private final HashMap<String, Command> commandMap = new HashMap<>();
    private final ArrayList<SequenceOfStops> orderSequenceOfStops = new ArrayList<>();
    private final ArrayList<Shaft> shafts = new ArrayList<>();
    private final ArrayList<Floor> floors = new ArrayList<>();
    @Value("${house.elevatorCountCabin}")
    private int shaftCount;
    @Value("${house.floorsCount}")
    private int floorCount;

    @Autowired
    @Lazy
    private FindNearestCabinCommand findNearestCabinCommand;

    @PostConstruct
    public void myInit () {
        for (int i = 0; i < shaftCount; ++i) {
            shafts.add(new Shaft());
        }

        floors.add(new FirstFloor(1));
        for (int id = 2; id < floorCount; ++id) {
            floors.add(new MiddleFloor(id));
        }

        System.out.println("Init started!");
        registerCommand("findNearestCabin", findNearestCabinCommand);
    }

    public void registerCommand (String nameCommand, Command command) {
        commandMap.put(nameCommand, command);
    }

    public void runCommands (String commandName, HashMap<String, Object> params) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new IllegalStateException("no command registered for " + commandName);
        }
        command.execute(params);
    }

    public void addSequenceToOrder () {
    }

    public void moveSequence () {
    }
}

