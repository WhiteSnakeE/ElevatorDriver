package com.sytoss.edu.elevator.unit.floors;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.UpFloorButton;
import com.sytoss.edu.elevator.bom.house.floors.MiddleFloor;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MiddleFloorTest {

    private final UpFloorButton upFloorButton = mock(UpFloorButton.class);

    private final House house = mock(House.class);

    @Test
    public void pressUpButton() {
        MiddleFloor middleFloor = new MiddleFloor(2, house, upFloorButton);
        middleFloor.pressUpButton(house);
        verify(upFloorButton).press(house, middleFloor.getFloorNumber());
    }
}
