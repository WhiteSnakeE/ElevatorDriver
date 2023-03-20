package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.Controller;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.floors.Floor;
import com.sytoss.edu.elevator.bom.floors.MiddleFloor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloorService {
    @Autowired
    private Controller controller;

    public void goUpCabinRequest (int floorNumber) {
        Floor floor = controller.getFloors().get(floorNumber - 1);

        if (floorNumber == 1) {
            ((FirstFloor) floor).pressUpButton();
        } else {
            ((MiddleFloor) floor).pressUpButton();
        }

        SequenceOfStops sequenceToAdd = new SequenceOfStops();
        sequenceToAdd.getStopFloors().add(floorNumber);
        sequenceToAdd.setDirection(Direction.UPWARDS);

        controller.addSequenceToOrder(sequenceToAdd);
        controller.runCommands();
    }
}
