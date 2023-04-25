package com.sytoss.edu.elevator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HouseThreadPool {
    private ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public synchronized ThreadPoolExecutor getFixedThreadPool() {
        return fixedThreadPool;
    }

    public synchronized void await() {
        log.info("IS TERMINATED {}", fixedThreadPool.isTerminated());
        fixedThreadPool.shutdown();
        try {
            fixedThreadPool.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("IS TERMINATED{}", fixedThreadPool.isTerminated());
        if (fixedThreadPool.isTerminated()) {
            fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        }
    }
}
