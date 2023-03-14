package com.sytoss.edu.elevator.controller;

import com.sytoss.edu.elevator.services.FloorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SayHelloController {
    @GetMapping("/helloWorld")
    public String sayHello () {
        return "Hello World!";
    }
}
