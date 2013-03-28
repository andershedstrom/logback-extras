package org.ahedstrom.logback.extras.appenders;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

public class JmxNotifier extends NotificationBroadcasterSupport implements JmxNotifierMBean {

    public static final String OBJECT_NAME = String.format("%s:type=LogbackAppender", JmxNotifier.class.getName());
    
    public JmxNotifier() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(this, new ObjectName(OBJECT_NAME));
    }
    
    public static ObjectName objectName() throws MalformedObjectNameException {
        return new ObjectName(OBJECT_NAME);
    }
    
    @Override
    public void sendNotification(Notification notification) {
        super.sendNotification(notification);
    }
}
