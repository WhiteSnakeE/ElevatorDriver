package com.sytoss.edu.elevator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class TestContext {

    private ResponseEntity<String> response;

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
