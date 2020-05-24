package me.cxis.lc.client.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class MBeanRegister {

    private static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();


    public static void registerMBean(String name, Object instance) {
        try {
            ObjectName objectName = new ObjectName(name);
            mBeanServer.registerMBean(instance, objectName);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }
}
