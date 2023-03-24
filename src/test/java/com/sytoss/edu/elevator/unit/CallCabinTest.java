package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class CallCabinTest extends IntegrationTest {
    private ResponseEntity<String> response;

    @Test
    public void goUpCabinRequest () {
        getElevatorDriver().getShafts().get(0).getEngine().setEngineState(EngineState.STAYING);
        getElevatorDriver().getShafts().get(0).setCabinPosition(3);

        getElevatorDriver().getShafts().get(1).getEngine().setEngineState(EngineState.STAYING);
        getElevatorDriver().getShafts().get(1).setCabinPosition(4);

        String url = "/api/floorButton/" + 5 + "/up";
        response = doPost(url, null, String.class);

        Assertions.assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        Assertions.assertNotNull(getElevatorDriver().getShafts().get(1).getSequenceOfStops());
        Assertions.assertEquals(Direction.UPWARDS, getElevatorDriver().getShafts().get(1).getSequenceOfStops().getDirection());

        Assertions.assertNull(getElevatorDriver().getShafts().get(0).getSequenceOfStops());

    }
}
