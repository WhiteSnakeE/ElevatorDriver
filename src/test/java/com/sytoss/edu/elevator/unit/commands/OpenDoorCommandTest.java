package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.OpenDoorCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.ListIterator;

import static com.sytoss.edu.elevator.commands.CommandManager.ITERATOR_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static org.mockito.Mockito.*;

public class OpenDoorCommandTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);
    private final OpenDoorCommand openDoorCommand = new OpenDoorCommand(shaftRepository);

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        Cabin cabin = mock(Cabin.class);
        House house = new HouseBuilder(mock(CommandManager.class)).build(2, 16);

        HashMap<String, Object> params = new HashMap<>();
        params.put(SHAFT_PARAM, shaft);
        params.put(ITERATOR_PARAM, house.getFloors().listIterator());

        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.OPENED);

        openDoorCommand.execute(params);

        verify(cabin).openDoor(shaft, (ListIterator) params.get(ITERATOR_PARAM));
        verify(shaftRepository).updateDoorStateById(0L, DoorState.OPENED);
    }
}
