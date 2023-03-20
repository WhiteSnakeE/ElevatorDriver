package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class FindNearestCabinCommand implements Command {
    @Autowired
    private Controller controller;

    @Override
    public void execute (HashMap<String, Object> params) {
        ArrayList<Shaft> shafts = controller.getShafts();
        ArrayList<Shaft> freeShafts = new ArrayList<>();

        for (Shaft shaft : shafts) {
            if (shaft.getSequenceOfStops() == null) {
                freeShafts.add(shaft);
            }
        }

        if (freeShafts.isEmpty()) {
            return;
        }

        int floorNumber = controller.getOrderSequenceOfStops().get(0).getStopFloors().get(0);
        int min = Integer.MAX_VALUE;
        Shaft nearestCabin = null;

        for (Shaft shaft : freeShafts) {
            if (Math.abs(floorNumber - shaft.getCabinPosition()) < min) {
                min = Math.abs(floorNumber - shaft.getCabinPosition());
                nearestCabin = shaft;
            }
        }

        if (nearestCabin == null) {
            return;
        }

        nearestCabin.updateSequence(controller.getOrderSequenceOfStops().get(0));
        controller.removeSequenceFromOrder(0);
    }
}
