package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CloseDoorCommand;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.ListIterator;

import static com.sytoss.edu.elevator.commands.CommandManager.ITERATOR_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static org.mockito.Mockito.*;

class CloseDoorCommandTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);
    private final CloseDoorCommand closeDoorCommand = new CloseDoorCommand(shaftRepository);

    @Test
    public synchronized void executeTest () {
        Shaft shaft = mock(Shaft.class);
        House house = new HouseBuilder(mock(CommandManager.class)).build(2, 16);

        HashMap<String, Object> params = new HashMap<>();

        params.put(SHAFT_PARAM, shaft);
        params.put(ITERATOR_PARAM, house.getFloors().listIterator());

        Cabin cabin = mock(Cabin.class);

        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.CLOSED);

        closeDoorCommand.execute(params);

        verify(cabin).closeDoor(shaft, (ListIterator) params.get(ITERATOR_PARAM));
        verify(shaftRepository).updateDoorStateById(0L, DoorState.CLOSED);
    }
}