package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShaftTest {
    @Test
    public void isFreeTest(){
        Shaft shaft= new Shaft();
        Assertions.assertTrue(shaft.isFree());
    }

    @Test
    public void updateSequenceAddTest(){
        Shaft shaft=new Shaft();
        SequenceOfStops sequenceOfStops=new SequenceOfStops();
        sequenceOfStops.setStopFloors(List.of(5));
        shaft.updateSequence(List.of(sequenceOfStops));

        Assertions.assertEquals(5,shaft.getSequenceOfStops().getStopFloors().get(0));
    }

    @Test
    public void updateSequenceMergeTest(){
        Shaft shaft=new Shaft();
        SequenceOfStops sequenceOfStops1=new SequenceOfStops();
        sequenceOfStops1.setStopFloors(List.of(5));
        shaft.setSequenceOfStops(sequenceOfStops1);

        SequenceOfStops sequenceOfStops2=new SequenceOfStops();
        sequenceOfStops2.setStopFloors(List.of(3));
        shaft.updateSequence(List.of(sequenceOfStops2));

        Assertions.assertEquals(List.of(3,5),shaft.getSequenceOfStops().getStopFloors());
    }

    @Test
    public void isSameDirectionTest(){
        Shaft shaft=new Shaft();
        shaft.setCabinPosition(1);

        Assertions.assertTrue(shaft.isSameDirection(Direction.UPWARDS,5));

        shaft.setCabinPosition(6);
        Assertions.assertFalse(shaft.isSameDirection(Direction.UPWARDS,5));
    }
}
