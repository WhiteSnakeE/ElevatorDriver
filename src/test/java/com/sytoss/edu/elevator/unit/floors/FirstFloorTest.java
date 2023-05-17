package com.sytoss.edu.elevator.unit.floors;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.UpFloorButton;
import com.sytoss.edu.elevator.bom.house.floors.FirstFloor;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FirstFloorTest {

    private final UpFloorButton upFloorButton = mock(UpFloorButton.class);

    @Test
    public void pressUpButton() {
        House house = mock(House.class);
        FirstFloor firstFloor = new FirstFloor(house, upFloorButton);
        firstFloor.pressUpButton(house);

        verify(upFloorButton).press(house, firstFloor.getFloorNumber());
    }
}
