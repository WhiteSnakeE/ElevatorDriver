package com.sytoss.edu.elevator.unit.converters;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class HouseConverterTest {

    private final HouseConverter houseConverter = new HouseConverter();

    private final CommandManager commandManager = mock(CommandManager.class);

    private final HouseBuilder houseBuilder = new HouseBuilder(commandManager);

    @Test
    public void toDTOTest() {
        House houseTmp = houseBuilder.build(2, 16);
        HouseDTO houseDTO = houseConverter.toDTO(houseTmp);

        Assertions.assertEquals(2, houseDTO.getNumberOfShafts());
        Assertions.assertEquals(16, houseDTO.getNumberOfFloors());
        Assertions.assertEquals(houseTmp.getId(), houseDTO.getId());
    }
}
