package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HouseBuilderTest {

    @Autowired
    private HouseBuilder houseBuilder;
    @Value("${house.elevatorCountCabin}")
    private int shaftCount;
    @Value("${house.floorsCount}")
    private int floorsCount;

    @Test
    public void buildTest () {
        House resultHouse = houseBuilder.build(shaftCount, floorsCount);

        Assertions.assertEquals(shaftCount, resultHouse.getShafts().size());
        Assertions.assertEquals(floorsCount, resultHouse.getFloors().size());
    }
}
