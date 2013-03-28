package org.ahedstrom.logback.extras.dropwizard.bundles;

import org.ahedstrom.logback.extras.appenders.JmxNotificationAppender;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Layout;

import com.yammer.dropwizard.ConfiguredBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.logging.AsyncAppender;
import com.yammer.dropwizard.logging.LogFormatter;

public abstract class JmxNotificationLoggingBundle<T extends Configuration> implements ConfiguredBundle<T> {

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    /**
     * Initializes the environment.
     *
     * @param configuration the {@link Configuration} object
     * @param environment   the service's {@link Environment}
     * @throws Exception if something goes wrong
     */
    @Override
    public void run(T configuration, Environment environment) throws Exception {
        final JmxNotificationLoggingConfiguration config = getConfiguration(configuration);

        if (config.isEnabled()) {
            Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            root.addAppender(AsyncAppender.wrap(buildJmxNotificationAppender(config, root.getLoggerContext())));
        } 
    }

    private Appender<ILoggingEvent> buildJmxNotificationAppender(JmxNotificationLoggingConfiguration config,
                                                                 LoggerContext loggerContext) {
        JmxNotificationAppender appender = new JmxNotificationAppender();
        appender.setContext(loggerContext);
        appender.setLayout(createLayout(loggerContext, config));
        addThresholdFilter(appender, config.getThreshold());
        appender.start();
        return appender;
    }

    private Layout<ILoggingEvent> createLayout(LoggerContext context, JmxNotificationLoggingConfiguration config) {
        final LogFormatter formatter = new LogFormatter(context, config.getTimeZone());
        if (config.getLogFormat() != null) {
            formatter.setPattern(config.getLogFormat());
        }
        formatter.start();
        return formatter;
    }

    private void addThresholdFilter(JmxNotificationAppender appender, Level threshold) {
        final ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel(threshold.toString());
        filter.start();
        appender.addFilter(filter);
    }

    public abstract JmxNotificationLoggingConfiguration getConfiguration(T configuration);
    
}
