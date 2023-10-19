package fun.pullock.lc.client;

import com.alibaba.fastjson.JSON;
import fun.pullock.lc.client.jmx.MBeanRegister;
import fun.pullock.lc.client.jmx.jvm.JVMRuntime;
import fun.pullock.lc.client.thread.LogCollectorThreadFactory;
import fun.pullock.lc.common.protocol.model.JVMRuntimeInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static fun.pullock.lc.common.constants.Constants.LOCAL_AGENT_HOST;
import static fun.pullock.lc.common.constants.Constants.LOCAL_AGENT_PORT;

public class LogCollector {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogCollector.class);

    private final static LogCollector logCollector;

    private static String appName;

    private final static String MBEAN_PREFIX = "fun.pullock.lc.";

    private ScheduledExecutorService connectToLocalAgentExecutor = Executors.newScheduledThreadPool(1, new LogCollectorThreadFactory("LogCollector-ConnectToLocalAgent"));
    private ScheduledFuture<?> connectToLocalAgentFuture;

    private ScheduledExecutorService sendJVMInfoToLocalAgentExecutor = Executors.newScheduledThreadPool(1, new LogCollectorThreadFactory("LogCollector-SentJVMInfoToLocalAgent"));
    private ScheduledFuture<?> sendJVMInfoToLocalAgentFuture;

    private Socket socket;
    private DataOutputStream dataOutputStream;

    private Lock sendLock = new ReentrantLock();

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

        // 注册JVM RuntimeMBean
        MBeanRegister.registerMBean(String.format("%s%s:type=%s", MBEAN_PREFIX, appName, "JVMRuntimeInfo"), JVMRuntime.getInstance());

        // 发送JVM信息到LocalAgent
        sendJVMInfoToLocalAgentFuture = sendJVMInfoToLocalAgentExecutor.scheduleAtFixedRate(new SendJVMInfoToLocalAgentThread(), 0, 10, TimeUnit.SECONDS);
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
                // 发送hello信息给LocalAgent
                sendMessageToLocalAgent(0, 1, appName);
            } catch (Exception e) {
                e.printStackTrace();
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
                    e1.printStackTrace();
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

    private class SendJVMInfoToLocalAgentThread implements Runnable {

        @Override
        public void run() {
            JVMRuntime jvmRuntime = JVMRuntime.getInstance();

            JVMRuntimeInfo runtimeInfo = new JVMRuntimeInfo();
            runtimeInfo.setBootClassPath(jvmRuntime.getBootClassPath());
            runtimeInfo.setBootClassPathSupported(jvmRuntime.isBootClassPathSupported());
            runtimeInfo.setClassPath(jvmRuntime.getClassPath());
            runtimeInfo.setInputArguments(jvmRuntime.getInputArguments());
            runtimeInfo.setLibraryPath(jvmRuntime.getLibraryPath());
            runtimeInfo.setName(jvmRuntime.getName());
            runtimeInfo.setSpecName(jvmRuntime.getSpecName());
            runtimeInfo.setManagementSpecVersion(jvmRuntime.getManagementSpecVersion());
            runtimeInfo.setSpecVendor(jvmRuntime.getSpecVendor());
            runtimeInfo.setStartTime(jvmRuntime.getStartTime());
            runtimeInfo.setUptime(jvmRuntime.getUptime());
            runtimeInfo.setSystemProperties(jvmRuntime.getSystemProperties());
            runtimeInfo.setVmName(jvmRuntime.getVmName());
            runtimeInfo.setVmVendor(jvmRuntime.getVmVendor());
            runtimeInfo.setSpecVersion(jvmRuntime.getSpecVersion());

            sendMessageToLocalAgent(0, 4, JSON.toJSONString(runtimeInfo));
        }
    }

    private void sendMessageToLocalAgent(int reqResType, int messageType, String message) {
        sendLock.lock();
        try {
            if (StringUtils.isEmpty(message)) {
                return;
            }

            if (dataOutputStream == null) {
                return;
            }

            dataOutputStream.writeByte(reqResType);
            dataOutputStream.writeByte(messageType);
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            dataOutputStream.writeInt(data.length);
            dataOutputStream.write(data);
            dataOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sendLock.unlock();
        }
    }
}
