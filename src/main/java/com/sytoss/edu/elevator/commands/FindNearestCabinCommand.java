package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class FindNearestCabinCommand implements Command {
    @Autowired
    private Controller controller;

    @Override
    public void execute (HashMap<String,Object>params) {
        ArrayList<Shaft> shafts = controller.getShafts();
        int floorInt = (int) params.get("floorNumber");

        shafts.get(0).setCabinPosition(3);
        shafts.get(1).setCabinPosition(4);
        Shaft shaftResult = shafts.get(0);
        int min = Integer.MAX_VALUE;
        for (Shaft shaft : shafts) {
            if (Math.abs(floorInt - shaft.getCabinPosition()) < min) {
                min = Math.abs(floorInt - shaft.getCabinPosition());
                shaftResult = shaft;
            }
        }

        SequenceOfStops sequenceToAdd = new SequenceOfStops();
        sequenceToAdd.setDirection(Direction.UPWARDS);
        sequenceToAdd.getStopFloors().add(floorInt);

        controller.addSequenceToOrder(sequenceToAdd);
    }
}
