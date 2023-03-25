package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

public class FindNearestCabinCommandTest extends IntegrationTest {
    @Autowired
    private FindNearestCabinCommand findNearestCabinCommand;
    @MockBean
    @Autowired
    private House house;


    @Test
    public void executeTest () {
        findNearestCabinCommand.execute(null);
        verify(house).moveSequenceToShaft();
    }


}
