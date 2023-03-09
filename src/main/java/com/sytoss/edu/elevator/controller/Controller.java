package com.sytoss.edu.elevator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping("/helloWorld")
    public String sayHello() {
        return "Hello World!";
    }
}
