package com.sytoss.edu.elevator.bom.house.floors;

import com.sytoss.edu.elevator.bom.Entity;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Floor extends Entity {

    @Getter
    private final int floorNumber;

    @Getter
    private final House house;

    public Floor(House house,int floorNumber) {
        this.floorNumber = floorNumber;
        this.house = house;

    }
}
