package com.sytoss.edu.elevator.converters;

import com.sytoss.edu.elevator.dto.ShaftDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Table(name = "SHAFT")
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ShaftConverter {

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

    @Column(name = "HOUSE_ID")
    private long houseId;
}
