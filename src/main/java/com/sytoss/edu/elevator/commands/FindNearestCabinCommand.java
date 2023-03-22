package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Command;
import com.sytoss.edu.elevator.bom.LiftDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class FindNearestCabinCommand implements Command {
    @Autowired
    private LiftDriver liftDriver;

    @Override
    public void execute (HashMap<String, Object> params) {
        ArrayList<Shaft> shafts = liftDriver.getShafts();
        ArrayList<Shaft> freeShafts = new ArrayList<>();

        for (Shaft shaft : shafts) {
            if (shaft.getSequenceOfStops() == null) {
                freeShafts.add(shaft);
            }
        }

        if (freeShafts.isEmpty()) {
            return;
        }

        int floorNumber = liftDriver.getOrderSequenceOfStops().get(0).getStopFloors().get(0);
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

        nearestCabin.setSequenceOfStops(liftDriver.getOrderSequenceOfStops().get(0));
        liftDriver.removeSequenceFromOrder(0);
    }
}
