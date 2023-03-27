package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class FindNearestCabinCommandTest {
    @Autowired
    private FindNearestCabinCommand findNearestCabinCommand;
    @MockBean
    @Autowired
    private House house;
    @Autowired
    private ElevatorDriver elevatorDriver;

    @Test
    public void executeTest () {
        findNearestCabinCommand.execute(null);
        verify(house).moveSequenceToShaft(elevatorDriver);
    }


}
