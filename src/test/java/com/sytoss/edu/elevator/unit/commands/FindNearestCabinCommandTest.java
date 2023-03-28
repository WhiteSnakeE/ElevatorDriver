package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class FindNearestCabinCommandTest {
    @Autowired
    private FindNearestCabinCommand findNearestCabinCommand;
    @SpyBean
    @Autowired
    private House house;
    @Autowired
    @SpyBean
    private ElevatorDriver elevatorDriver;

    @Test
    public void executeTest () {
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);

        findNearestCabinCommand.execute(null);
        verify(house).moveSequenceToShaft(elevatorDriver.getOrderSequenceOfStops());
        verify(elevatorDriver).removeSequenceFromOrder();
    }


}
