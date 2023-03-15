package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.entities.Controller;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseService {
    private final Controller controller;
}
