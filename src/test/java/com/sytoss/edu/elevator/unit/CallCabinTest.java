package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.EngineState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class CallCabinTest extends IntegrationTest {
    private ResponseEntity<String> response;

    @Test
    public void goUpCabinRequest () {
        getLiftDriver().getShafts().get(0).getEngine().setEngineState(EngineState.STAYING);
        getLiftDriver().getShafts().get(0).setCabinPosition(3);

        getLiftDriver().getShafts().get(1).getEngine().setEngineState(EngineState.STAYING);
        getLiftDriver().getShafts().get(1).setCabinPosition(4);

        String url = "/api/floorButton/" + 5 + "/up";
        response = doPost(url, null, String.class);

        Assertions.assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        Assertions.assertNotNull(getLiftDriver().getShafts().get(1).getSequenceOfStops());
        Assertions.assertEquals(Direction.UPWARDS, getLiftDriver().getShafts().get(1).getSequenceOfStops().getDirection());

        Assertions.assertNull(getLiftDriver().getShafts().get(0).getSequenceOfStops());

    }
}
