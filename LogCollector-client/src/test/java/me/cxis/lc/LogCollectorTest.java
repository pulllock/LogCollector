package me.cxis.lc;

import org.junit.Test;

public class LogCollectorTest {

    @Test
    public void testStart() {
        LogCollector logCollector = LogCollector.newLogCollector();
        logCollector.setAppName("test-app-name");
        logCollector.start();
    }
}
