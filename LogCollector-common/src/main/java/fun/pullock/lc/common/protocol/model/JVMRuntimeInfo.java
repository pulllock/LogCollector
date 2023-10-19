package fun.pullock.lc.common.protocol.model;

import java.io.Serializable;

public class JVMRuntimeInfo implements Serializable {

    private static final long serialVersionUID = -7455399902421844999L;

    public String bootClassPath;

    public boolean isBootClassPathSupported;

    public String classPath;

    public String inputArguments;

    public String libraryPath;

    public String managementSpecVersion;

    public String name;

    public String specName;

    public String specVendor;

    public String specVersion;

    public long startTime;

    public long uptime;

    public String systemProperties;

    public String vmName;

    public String vmVendor;

    public String vmVersion;

    public String getBootClassPath() {
        return bootClassPath;
    }

    public void setBootClassPath(String bootClassPath) {
        this.bootClassPath = bootClassPath;
    }

    public boolean isBootClassPathSupported() {
        return isBootClassPathSupported;
    }

    public void setBootClassPathSupported(boolean bootClassPathSupported) {
        isBootClassPathSupported = bootClassPathSupported;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getInputArguments() {
        return inputArguments;
    }

    public void setInputArguments(String inputArguments) {
        this.inputArguments = inputArguments;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }

    public String getManagementSpecVersion() {
        return managementSpecVersion;
    }

    public void setManagementSpecVersion(String managementSpecVersion) {
        this.managementSpecVersion = managementSpecVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecVendor() {
        return specVendor;
    }

    public void setSpecVendor(String specVendor) {
        this.specVendor = specVendor;
    }

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public String getSystemProperties() {
        return systemProperties;
    }

    public void setSystemProperties(String systemProperties) {
        this.systemProperties = systemProperties;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmVendor() {
        return vmVendor;
    }

    public void setVmVendor(String vmVendor) {
        this.vmVendor = vmVendor;
    }

    public String getVmVersion() {
        return vmVersion;
    }

    public void setVmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
    }

    @Override
    public String toString() {
        return "JVMRuntimeInfo{" +
            "bootClassPath='" + bootClassPath + '\'' +
            ", isBootClassPathSupported=" + isBootClassPathSupported +
            ", classPath='" + classPath + '\'' +
            ", inputArguments='" + inputArguments + '\'' +
            ", libraryPath='" + libraryPath + '\'' +
            ", managementSpecVersion='" + managementSpecVersion + '\'' +
            ", name='" + name + '\'' +
            ", specName='" + specName + '\'' +
            ", specVendor='" + specVendor + '\'' +
            ", specVersion='" + specVersion + '\'' +
            ", startTime=" + startTime +
            ", uptime=" + uptime +
            ", systemProperties='" + systemProperties + '\'' +
            ", vmName='" + vmName + '\'' +
            ", vmVendor='" + vmVendor + '\'' +
            ", vmVersion='" + vmVersion + '\'' +
            '}';
    }
}
