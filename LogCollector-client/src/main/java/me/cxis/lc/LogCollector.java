package me.cxis.lc;

import me.cxis.lc.thread.LogCollectorThreadFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static me.cxis.lc.constants.Constants.LOCAL_AGENT_HOST;
import static me.cxis.lc.constants.Constants.LOCAL_AGENT_PORT;

public class LogCollector {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogCollector.class);

    private final static LogCollector logCollector;

    private static String appName;

    private ScheduledExecutorService connectToLocalAgentExecutor = Executors.newScheduledThreadPool(1, new LogCollectorThreadFactory("LogCollector-ConnectToLocalAgent"));
    private ScheduledFuture<?> connectToLocalAgentFuture;

    private Socket socket;
    private DataOutputStream dataOutputStream;

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

        // 打开到localAgent的Socket连接，使用定时任务，每分钟扫描一次Socket是否为空
        // 如果Socket出现了问题，发送数据的时候有异常，会将Socket重置，定时任务检测到Socket重置后
        // 可以重新创建一个Socket连接
        connectToLocalAgentFuture = connectToLocalAgentExecutor.scheduleWithFixedDelay(new ConnectToLocalAgentThread(), 0, 30, TimeUnit.SECONDS);

        // TODO 发送心跳，定时任务

        // 发送JVM信息到LocalAgent
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        LogCollector.appName = appName;
    }

    private class ConnectToLocalAgentThread implements Runnable {

        private ConnectToLocalAgentThread() {}

        @Override
        public void run() {
            if (socket != null) {
                return;
            }

            try {
                socket = new Socket(LOCAL_AGENT_HOST, LOCAL_AGENT_PORT);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                // TODO 发送hello信息给LocalAgent
            } catch (Exception e) {
                LOGGER.error("Connect to local agent error, cause: ", e);
                try {
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }

                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                        dataOutputStream = null;
                    }
                } catch (Exception e1) {
                    LOGGER.error("Connect to local agent error, cause: ", e1);
                }
            }
        }
    }

    public void stop() {
        if (connectToLocalAgentFuture != null && connectToLocalAgentFuture.cancel(true)) {
            connectToLocalAgentFuture = null;
        }

        if (connectToLocalAgentExecutor != null) {
            connectToLocalAgentExecutor.shutdown();
        }
    }
}
