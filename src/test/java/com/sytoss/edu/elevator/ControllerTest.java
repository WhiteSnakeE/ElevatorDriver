package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.controller.Controller;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class ControllerTest {
    @Test
    public void testHelloWorld() {
        Controller controller = new Controller();
        Assert.assertEquals("Hello World!", controller.sayHello());
    }
}