package com.sytoss.edu.elevator.bom;

import lombok.Getter;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Entity {

    protected final Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

    public Entity () {
        log.info("Entity id is {}", getId());
    }

}