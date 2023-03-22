package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import io.cucumber.java.After;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CucumberHooks extends IntegrationTest {

    @After
    public void tearDown () {
        TestContext.dropInstance();
        log.info("tearDown completed...");
    }
}
