package com.sytoss.edu.elevator.bom.house.floors;

import com.sytoss.edu.elevator.bom.Entity;
import com.sytoss.edu.elevator.bom.Shaft;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public abstract class Floor extends Entity {

    @Getter
    private final int floorNumber;
    private final int timeSleep=0;

    public Floor (int floorNumber) {
        this.floorNumber = floorNumber;
    }
    public void visit(Shaft shaft){
        log.info("Shaft with id [{}] is on floor №: [{}]", shaft.getId(), shaft.getCabinPosition());
        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
