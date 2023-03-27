package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.Shaft;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShaftTest {
    @Test
    public void isFree(){
        Shaft shaft= new Shaft();
        Assertions.assertTrue(shaft.isFree());
    }

}
