package me.cxis.lc.client;

import org.junit.Test;

public class LogCollectorTest {

    @Test
    public void testStart() throws InterruptedException {
        LogCollector logCollector = LogCollector.newLogCollector();
        logCollector.setAppName("test-app-name");
        logCollector.start();
    }
}
