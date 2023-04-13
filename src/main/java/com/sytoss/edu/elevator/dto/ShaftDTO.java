package com.sytoss.edu.elevator.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShaftDTO {

    private long id;

    private int cabinPosition;

    private String doorState;

    private String engineState;

    private String overweightState;

    private long houseId;
}
