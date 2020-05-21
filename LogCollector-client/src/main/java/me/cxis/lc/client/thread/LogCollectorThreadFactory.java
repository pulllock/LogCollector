package me.cxis.lc.client.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class LogCollectorThreadFactory implements ThreadFactory {

    private final static AtomicInteger poolNumber = new AtomicInteger(1);

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final ThreadGroup group;

    private final String threadNamePrefix;

    public LogCollectorThreadFactory(String prefix) {
        SecurityManager securityManager = System.getSecurityManager();

        group = securityManager == null ? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();

        threadNamePrefix = String.format("%s-%d-", prefix, poolNumber.getAndIncrement());
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(
            group,
            r,
            threadNamePrefix + threadNumber.getAndIncrement(),
            0
        );

        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
