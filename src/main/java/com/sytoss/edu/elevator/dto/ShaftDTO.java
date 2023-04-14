package com.sytoss.edu.elevator.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "SHAFT")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShaftDTO {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "SEQUENCE_OF_STOPS")
    private String sequenceOfStops;

    @Column(name = "CABIN_POSITION")
    private int cabinPosition;

    @Column(name = "DOOR_STATE")
    private String doorState;

    @Column(name = "ENGINE_STATE")
    private String engineState;

    @Column(name = "OVERWEIGHT_STATE")
    private String overweightState;

    @ManyToOne
    @JoinColumn(name = "HOUSE_ID", referencedColumnName = "ID")
    private HouseDTO houseDTO;
}
