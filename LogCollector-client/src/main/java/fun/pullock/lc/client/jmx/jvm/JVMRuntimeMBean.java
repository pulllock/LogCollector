package fun.pullock.lc.client.jmx.jvm;

public interface JVMRuntimeMBean {

    String getBootClassPath();

    boolean isBootClassPathSupported();

    String getClassPath();

    String getInputArguments();

    String getLibraryPath();

    String getManagementSpecVersion();

    String getName();

    String getSpecName();

    String getSpecVendor();

    String getSpecVersion();

    long getStartTime();

    long getUptime();

    String getSystemProperties();

    String getVmName();

    String getVmVendor();

    String getVmVersion();
}
