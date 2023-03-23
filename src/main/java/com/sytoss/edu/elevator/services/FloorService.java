package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.LiftDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.floors.Floor;
import com.sytoss.edu.elevator.bom.floors.MiddleFloor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloorService {
    @Autowired
    private LiftDriver liftDriver;

    public void goUpCabinRequest (int floorNumber) {
        Floor floor = liftDriver.getFloors().get(floorNumber - 1);
        ((FloorWithUpButton) floor).pressUpButton();

        SequenceOfStops sequenceToAdd = new SequenceOfStops();
        sequenceToAdd.getStopFloors().add(floorNumber);
        sequenceToAdd.setDirection(Direction.UPWARDS);

        liftDriver.addSequenceToOrder(sequenceToAdd);
        liftDriver.runCommands();
    }
}
