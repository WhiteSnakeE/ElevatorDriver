package com.sytoss.edu.elevator.bom;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Entity {
    private Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
}