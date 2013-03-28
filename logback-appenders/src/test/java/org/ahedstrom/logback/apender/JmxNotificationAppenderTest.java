package org.ahedstrom.logback.apender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.management.ManagementFactory;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;

import org.ahedstrom.logback.extras.appenders.JmxNotifier;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

public class JmxNotificationAppenderTest {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class);
    static JmxNotificationListener listener;
    
    @BeforeClass
    public static void beforeClass() throws Exception {
        listener = createNotificationListener();
    }
    
    @Test
    public void info() throws Exception {
        LOG.info("TESTING TESTING");
        
        assertEquals(listener.notification.getType(), Level.INFO.toString());
        assertTrue(listener.notification.getMessage().contains("TESTING TESTING"));
        assertNull(listener.notification.getUserData());
    }
    
    @Test
    public void error() {
        LOG.error("ERROR ERROR", new RuntimeException("EXCEPTION"));
        assertEquals(listener.notification.getType(), Level.ERROR.toString());
        assertTrue(listener.notification.getMessage().contains("ERROR ERROR"));
        assertNotNull(listener.notification.getUserData());
    }

    private static JmxNotificationListener createNotificationListener() throws InstanceNotFoundException, MalformedObjectNameException {
        JmxNotificationListener listener = new JmxNotificationListener();
        
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.addNotificationListener(JmxNotifier.objectName(), listener, null, null);
        
        return listener;
    }

    private static class JmxNotificationListener implements NotificationListener {
        private Notification notification;

        @Override
        public void handleNotification(Notification notification, Object handback) {
            this.notification = notification;
        }
    }
    
}
