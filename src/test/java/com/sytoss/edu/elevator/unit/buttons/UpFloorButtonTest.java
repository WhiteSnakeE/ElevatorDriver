package com.sytoss.edu.elevator.unit.buttons;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.UpFloorButton;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
public class UpFloorButtonTest {

    @Mock
    private Command command;


    @Test
    public void pressTest() {
        House house = mock(House.class);
        UpFloorButton upFloorButton = new UpFloorButton(command, house);
        upFloorButton.press(5);

        HashMap<String, Object> params = new HashMap<>();

        params.put("numberFloor", 5);
        params.put(CommandManager.HOUSE_PARAM, house);
        params.put("Direction", Direction.UPWARDS);


        verify(command).execute(params);
    }
}
