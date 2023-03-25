package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HouseBuilderTest extends IntegrationTest {
    @Autowired
    private HouseBuilder houseBuilder;

    @Test
    public void buildTest () {
        House resultHouse = houseBuilder.build(2, 16);

        Assertions.assertNotNull(resultHouse.getElevatorDriver());
        Assertions.assertEquals(2, resultHouse.getShafts().size());
        Assertions.assertEquals(16, resultHouse.getFloors().size());
    }
}
