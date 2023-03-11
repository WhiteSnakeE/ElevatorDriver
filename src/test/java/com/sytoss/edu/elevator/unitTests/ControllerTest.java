package com.sytoss.edu.elevator.unitTests;

import com.sytoss.edu.elevator.controller.Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ControllerTest {
    @Test
    public void testHelloWorld () {
        Controller controller = new Controller();
        Assertions.assertEquals("Hello World!", controller.sayHello());
    }
}