package com.sytoss.edu.elevator.converters;

import com.sytoss.edu.elevator.dto.HouseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Table(name = "house")
@NoArgsConstructor
@AllArgsConstructor
public class HouseConverter {

    @Id
    @Column(name = "ID")
    @Getter
    private long id;

    @Column(name = "NUMBEROFFLOORS")
    private int numberOfFloors;

    @Column(name = "NUMBEROFSHAFTS")
    private int numberOfShafts;

    @Column(name = "ORDERSEQUENCEOFSTOPS")
    private String orderSequenceOfStops;

    public HouseConverter fromDTO(HouseDTO houseDTO) {
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        return HouseConverter.builder().id(id).numberOfFloors(houseDTO.getNumberOfFloors()).numberOfShafts(houseDTO.getNumberOfShafts()).build();
    }
}
