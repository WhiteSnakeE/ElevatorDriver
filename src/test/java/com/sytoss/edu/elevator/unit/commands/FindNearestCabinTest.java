package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.AbstractJunitTest;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.EngineState;
import com.sytoss.edu.elevator.bom.LiftDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FindNearestCabinTest extends AbstractJunitTest {

    @Autowired
    private LiftDriver liftDriver;

    @Test
    public void execute () {
        liftDriver.getShafts().get(0).setCabinPosition(3);
        liftDriver.getShafts().get(0).getEngine().setEngineState(EngineState.STAYING);

        liftDriver.getShafts().get(1).setCabinPosition(4);
        liftDriver.getShafts().get(1).getEngine().setEngineState(EngineState.STAYING);

        SequenceOfStops sequenceOfStops = new SequenceOfStops();
        sequenceOfStops.setDirection(Direction.UPWARDS);
        sequenceOfStops.getStopFloors().add(5);

        liftDriver.getOrderSequenceOfStops().add(sequenceOfStops);

        liftDriver.runCommands();

        Assertions.assertEquals(0, liftDriver.getOrderSequenceOfStops().size());
        Assertions.assertEquals(5, liftDriver.getShafts().get(1).getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.UPWARDS, liftDriver.getShafts().get(1).getSequenceOfStops().getDirection());

        Assertions.assertNull(liftDriver.getShafts().get(0).getSequenceOfStops());

    }


}
