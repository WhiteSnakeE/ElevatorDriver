package com.sytoss.edu.elevator.unitTests;

import com.sytoss.edu.elevator.AbstractJunitTest;
import com.sytoss.edu.elevator.bom.Controller;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class ControllerUnitTest extends AbstractJunitTest {

    //Creating inject mock: controller.
    //This should help us to inject other mocks
    @InjectMocks
    private Controller mockController;

    //Creating mock: FindNearestCabinCommand
    //This mock should help us to test certain functions
    @Mock
    private FindNearestCabinCommand mockFindNearestCabinCommand;

    @Test
    public void controllerInitTest() {
        mockController.setShaftCount(2);
        mockController.setFloorCount(10);

        mockController.myInit();

        Assertions.assertEquals(2, mockController.getShafts().size());
        Assertions.assertEquals(10, mockController.getFloors().size());
        Assertions.assertEquals(1, mockController.getCommandMap().size());
    }

    @Test
    public void executeCommandFailed() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> mockController.executeCommand("findNearestCabin", null)
        );
    }

    @Test
    public void addSequenceToOrderTest() {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.UPWARDS);
        sequence.getStopFloors().add(5);
        sequence.setCurrentFloor(5);

        mockController.addSequenceToOrder(sequence);
        Assertions.assertNotEquals(0, mockController
                .getOrderSequenceOfStops()
                .size()
        );

        Assertions.assertEquals(sequence, mockController
                .getOrderSequenceOfStops()
                .get(0)
        );
    }

    @Test
    public void registerCommandTest() {
        mockController.registerCommand("findNearestCabin", mockFindNearestCabinCommand);
        Assertions.assertNotEquals(0, mockController.getCommandMap().size());
    }

    @Test
    public void removeSequenceFromOrderTest() {
        SequenceOfStops sequenceUpwards = new SequenceOfStops();
        sequenceUpwards.setDirection(Direction.UPWARDS);
        sequenceUpwards.getStopFloors().addAll(Arrays.asList(2, 7, 10));
        sequenceUpwards.setCurrentFloor(1);

        SequenceOfStops sequenceDownwards = new SequenceOfStops();
        sequenceDownwards.setDirection(Direction.DOWNWARDS);
        sequenceDownwards.getStopFloors().addAll(Arrays.asList(10, 9, 6));
        sequenceDownwards.setCurrentFloor(9);

        mockController.addSequenceToOrder(sequenceUpwards);
        mockController.addSequenceToOrder(sequenceDownwards);

        Assertions.assertEquals(2, mockController
                .getOrderSequenceOfStops()
                .size()
        );

        Assertions.assertEquals(Arrays.asList(2, 7, 10), mockController
                .getOrderSequenceOfStops()
                .get(0)
                .getStopFloors()
        );

        Assertions.assertEquals(Arrays.asList(10, 9, 6), mockController
                .getOrderSequenceOfStops()
                .get(1)
                .getStopFloors()
        );

        mockController.removeSequenceFromOrder(0);

        Assertions.assertEquals(1, mockController.
                getOrderSequenceOfStops().
                size()
        );

        Assertions.assertEquals(Arrays.asList(10, 9, 6), mockController
                .getOrderSequenceOfStops()
                .get(0)
                .getStopFloors()
        );
    }

    // This nest was created 'cause tests below need beforeEach initialization
    @Nested
    public class NestedControllerTestBlock {

        @BeforeEach
        public void mockControllerInit() {
            mockController.registerCommand("findNearestCabin", mockFindNearestCabinCommand);
        }

        @Test
        public void runCommandsTest() {
            doNothing().when(mockFindNearestCabinCommand).execute(any());
            mockController.runCommands();
            verify(mockFindNearestCabinCommand).execute(null);
        }

        @Test
        public void executeCommandTest() {
            doNothing().when(mockFindNearestCabinCommand).execute(any());

            HashMap<String, Object> testParams = new HashMap<>();
            testParams.put("test parameter 1", 123);
            testParams.put("test parameter 2", "Hello World!");

            mockController.executeCommand("findNearestCabin", testParams);
            verify(mockFindNearestCabinCommand).execute(testParams);
        }
    }
}
