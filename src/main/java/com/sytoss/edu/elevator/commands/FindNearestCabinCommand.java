package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.entities.Controller;
import com.sytoss.edu.elevator.interfaces.Command;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
@RequiredArgsConstructor
public class FindNearestCabinCommand implements Command {
    private final Controller controller;

    @Override
    public void execute (HashMap<String,String>params) {
        controller.findNearestCabin(Integer.parseInt(params.get("floorNumber")));
    }
}
