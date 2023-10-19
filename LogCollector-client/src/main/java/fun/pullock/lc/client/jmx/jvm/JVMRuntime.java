package fun.pullock.lc.client.jmx.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class JVMRuntime implements JVMRuntimeMBean {

    private static final JVMRuntime instance;

    private final RuntimeMXBean runtimeMXBean;

    static {
        instance = new JVMRuntime();
    }

    public static JVMRuntime getInstance() {
        return instance;
    }

    private JVMRuntime() {
        runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    }

    @Override
    public String getBootClassPath() {
        return runtimeMXBean.getBootClassPath();
    }

    @Override
    public boolean isBootClassPathSupported() {
        return runtimeMXBean.isBootClassPathSupported();
    }

    @Override
    public String getClassPath() {
        return runtimeMXBean.getClassPath();
    }

    @Override
    public String getInputArguments() {
        return runtimeMXBean.getInputArguments().toString();
    }

    @Override
    public String getLibraryPath() {
        return runtimeMXBean.getLibraryPath();
    }

    @Override
    public String getManagementSpecVersion() {
        return runtimeMXBean.getManagementSpecVersion();
    }

    @Override
    public String getName() {
        return runtimeMXBean.getName();
    }

    @Override
    public String getSpecName() {
        return runtimeMXBean.getSpecName();
    }

    @Override
    public String getSpecVendor() {
        return runtimeMXBean.getSpecVendor();
    }

    @Override
    public String getSpecVersion() {
        return runtimeMXBean.getSpecVersion();
    }

    @Override
    public long getStartTime() {
        return runtimeMXBean.getStartTime();
    }

    @Override
    public long getUptime() {
        return runtimeMXBean.getUptime();
    }

    @Override
    public String getSystemProperties() {
        return runtimeMXBean.getSystemProperties().toString();
    }

    @Override
    public String getVmName() {
        return runtimeMXBean.getVmName();
    }

    @Override
    public String getVmVendor() {
        return runtimeMXBean.getVmVendor();
    }

    @Override
    public String getVmVersion() {
        return runtimeMXBean.getVmVersion();
    }
}
