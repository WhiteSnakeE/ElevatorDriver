package com.sytoss.edu.elevator.converters;

import com.sytoss.edu.elevator.dto.HouseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Table(name = "HOUSE")
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class HouseConverter {

    @Id
    @Column(name = "ID")
    @Getter
    private long id;

    @Column(name = "NUMBER_OF_FLOORS")
    private int numberOfFloors;

    @Column(name = "NUMBER_OF_SHAFTS")
    private int numberOfShafts;

    @Column(name = "ORDER_SEQUENCE_OF_STOPS")
    private String orderSequenceOfStops;
}
