package com.sytoss.edu.elevator.bom.house.floors;

import com.sytoss.edu.elevator.bom.Entity;
import lombok.Getter;

public abstract class Floor extends Entity {

    @Getter
    private final int floorNumber;

    public Floor (int floorNumber) {
        this.floorNumber = floorNumber;
    }
}
