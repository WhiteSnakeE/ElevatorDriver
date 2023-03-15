package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.controller.FloorController;
import com.sytoss.edu.elevator.entities.Controller;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class Main {
    public static void main (String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
