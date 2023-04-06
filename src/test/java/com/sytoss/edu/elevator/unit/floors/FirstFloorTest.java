package com.sytoss.edu.elevator.unit.floors;

import com.sytoss.edu.elevator.bom.house.buttons.UpFloorButton;
import com.sytoss.edu.elevator.bom.house.floors.FirstFloor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FirstFloorTest {

    @Mock
    private UpFloorButton upFloorButton=mock(UpFloorButton.class);

    @Test
    public void pressUpButton () {
        FirstFloor firstFloor = new FirstFloor(upFloorButton);
        firstFloor.pressUpButton();

        verify(upFloorButton).press(firstFloor.getFloorNumber());
    }
}
