package com.sytoss.edu.elevator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HouseThreadPool {
    private ScheduledExecutorService fixedThreadPool = Executors.newScheduledThreadPool(4);

    public synchronized ScheduledExecutorService getFixedThreadPool() {
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
        log.info("IS TERMINATED {}", fixedThreadPool.isTerminated());
        if (fixedThreadPool.isTerminated()) {
            fixedThreadPool = Executors.newScheduledThreadPool(4);
        }
    }
}
