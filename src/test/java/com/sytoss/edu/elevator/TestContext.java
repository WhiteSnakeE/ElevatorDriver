package com.sytoss.edu.elevator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

@Getter
@Setter
public class TestContext {

    private ResponseEntity<String> response;

    private HashMap<Integer, Long> housesId = new HashMap<>();

    private static final ThreadLocal<TestContext> testContext = new ThreadLocal<>();

    public static TestContext getInstance() {
        if (testContext.get() == null) {
            testContext.set(new TestContext());
        }
        return testContext.get();
    }

    public static void dropInstance() {
        testContext.set(null);
    }
}
