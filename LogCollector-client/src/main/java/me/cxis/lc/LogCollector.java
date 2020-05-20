package me.cxis.lc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogCollector {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogCollector.class);

    private final static LogCollector logCollector;

    private static String appName;


    static {
        logCollector = new LogCollector();
    }

    private LogCollector () {}

    public static LogCollector newLogCollector() {
        return LogCollector.logCollector;
    }

    public synchronized void start() {
        if (StringUtils.isEmpty(appName)) {
            LOGGER.error("LogCollector appName is null!");
            throw new RuntimeException("LogCollector appName is null!");
        }


    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        LogCollector.appName = appName;
    }
}
