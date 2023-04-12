package com.sytoss.edu.elevator.converters;

import com.sytoss.edu.elevator.dto.ShaftDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "shaft")
@NoArgsConstructor
@AllArgsConstructor
public class ShaftConverter {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "SEQUENCEOFSTOPS")
    private String sequenceOfStops;

    @Column(name = "CABINPOSITION")
    private int cabinPosition;

    @Column(name = "DOORSTATE")
    private String doorState;

    @Column(name = "ENGINESTATE")
    private String engineState;

    @Column(name = "OVERWEIGHTSTATE")
    private String overweightState;

    @Column(name = "HOUSEID")
    private long houseId;

    public ShaftConverter fromDTO(ShaftDTO shaftDTO) {
        return ShaftConverter.builder().id(shaftDTO.getId()).cabinPosition(shaftDTO.getCabinPosition()).doorState(shaftDTO.getDoorState())
                .engineState(shaftDTO.getEngineState()).overweightState(shaftDTO.getOverweightState()).houseId(shaftDTO.getHouseId()).build();
    }
}
