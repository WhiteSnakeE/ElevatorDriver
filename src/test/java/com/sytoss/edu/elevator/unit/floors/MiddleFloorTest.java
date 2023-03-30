package com.sytoss.edu.elevator.unit.floors;

import com.sytoss.edu.elevator.bom.house.buttons.UpFloorButton;
import com.sytoss.edu.elevator.bom.house.floors.MiddleFloor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
public class MiddleFloorTest {

    @Mock
    private UpFloorButton upFloorButton;

    @Test
    public void pressUpButton () {
        MiddleFloor middleFloor = new MiddleFloor(2, upFloorButton);
        middleFloor.pressUpButton();

        verify(upFloorButton).press(middleFloor.getFloorNumber());
    }
}
