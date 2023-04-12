package com.sytoss.edu.elevator.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HouseDTO {

    private int numberOfFloors;

    private int numberOfShafts;
}
