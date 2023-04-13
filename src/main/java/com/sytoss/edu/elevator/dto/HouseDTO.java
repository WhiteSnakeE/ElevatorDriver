package com.sytoss.edu.elevator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
public class HouseDTO {

    private int numberOfFloors;

    private int numberOfShafts;
}
