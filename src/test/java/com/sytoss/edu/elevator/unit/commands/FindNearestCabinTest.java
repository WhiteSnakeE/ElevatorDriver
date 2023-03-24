package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.AbstractJunitTest;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FindNearestCabinTest extends AbstractJunitTest {

    @Autowired
    private ElevatorDriver elevatorDriver;

    @Test
    public void execute () {
        elevatorDriver.getShafts().get(0).setCabinPosition(3);
        elevatorDriver.getShafts().get(0).getEngine().setEngineState(EngineState.STAYING);

        elevatorDriver.getShafts().get(1).setCabinPosition(4);
        elevatorDriver.getShafts().get(1).getEngine().setEngineState(EngineState.STAYING);

        SequenceOfStops sequenceOfStops = new SequenceOfStops();
        sequenceOfStops.setDirection(Direction.UPWARDS);
        sequenceOfStops.getStopFloors().add(5);

        elevatorDriver.getOrderSequenceOfStops().add(sequenceOfStops);

        elevatorDriver.runCommands();

        Assertions.assertEquals(0, elevatorDriver.getOrderSequenceOfStops().size());
        Assertions.assertEquals(5, elevatorDriver.getShafts().get(1).getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.UPWARDS, elevatorDriver.getShafts().get(1).getSequenceOfStops().getDirection());

        Assertions.assertNull(elevatorDriver.getShafts().get(0).getSequenceOfStops());

    }


}
