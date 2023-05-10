package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.enums.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SequenceOfStopsTest {

    private final SequenceOfStops sequenceOfStops = new SequenceOfStops();

    @BeforeEach
    public void setup() {
        sequenceOfStops.setDirection(Direction.UPWARDS);
        sequenceOfStops.setStopFloors(List.of(3, 4, 5, 6));
    }

    @Test
    public void isFirstTrueTest() {
        Assertions.assertTrue(sequenceOfStops.isFirst(3));
    }

    @Test
    public void isFirstFalseTest() {
        Assertions.assertFalse(sequenceOfStops.isFirst(6));
    }

    @Test
    public void isLastTrueTest() {
        Assertions.assertTrue(sequenceOfStops.isLast(6));
    }

    @Test
    public void isLastFalseTest() {
        Assertions.assertFalse(sequenceOfStops.isLast(4));
    }
}
