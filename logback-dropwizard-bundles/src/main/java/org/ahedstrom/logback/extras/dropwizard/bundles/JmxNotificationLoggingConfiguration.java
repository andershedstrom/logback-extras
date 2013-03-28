package org.ahedstrom.logback.extras.dropwizard.bundles;

import java.util.TimeZone;

import javax.validation.constraints.NotNull;

import ch.qos.logback.classic.Level;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class JmxNotificationLoggingConfiguration extends Configuration {
    static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    
    @JsonProperty
    private boolean enabled = false;
    @NotNull
    @JsonProperty
    private Level threshold = Level.ALL;
    @NotNull
    @JsonProperty
    private TimeZone timeZone = UTC;
    @JsonProperty
    private String logFormat;
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public Level getThreshold() {
        return this.threshold;
    }
    
    public void setThreshold(Level threshold) {
        this.threshold = threshold;
    }
    
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    public String getLogFormat() {
        return this.logFormat;
    }

    public void setLogFormat(String logFormat) {
        this.logFormat = logFormat;
    }
}
