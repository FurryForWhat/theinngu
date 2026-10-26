package com.theinngu32.common.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

import static org.apache.logging.log4j.Level.*;

@Component
public class Log4j2Config {

    @Value("${environment:local}")
    private String environment;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String USER_HOME = System.getProperty("user.home");

    @Bean
    public Logger log4j2Logger() {
        String logDir = getLogDirectory();

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        // Create Pattern Layout
        PatternLayout patternLayout = createPatternLayout();

        // Create and register appenders
        RollingFileAppender rollingFileAppender = createRollingFileAppender(logDir, patternLayout);
        rollingFileAppender.start(); // Explicitly start the RollingFileAppender

        ConsoleAppender consoleAppender = createConsoleAppender(patternLayout);
        consoleAppender.start(); // Explicitly start the ConsoleAppender

        config.addAppender(rollingFileAppender);
        config.addAppender(consoleAppender);

        // Configure loggers
        configureLogger(config, "org.springframework", WARN, rollingFileAppender);
        configureLogger(config, "org.springframework.boot", WARN, rollingFileAppender);
        configureLogger(config, "org.hibernate", ERROR, rollingFileAppender);
        configureLogger(config, "com.theinngu32", DEBUG, rollingFileAppender);
        configureLogger(config, LogManager.ROOT_LOGGER_NAME, INFO, rollingFileAppender);

        // Apply changes and ensure the context is started
        context.updateLoggers();
        context.start();

        return LogManager.getLogger(Log4j2Config.class);
    }

    private String getLogDirectory() {
        String logDir = "local".equals(environment)
                ? Paths.get(USER_HOME, "Documents", applicationName, "logs").toString()
                : "logs";

        File logDirectory = new File(logDir);
        if (!logDirectory.exists()) {
            logDirectory.mkdirs();
        }
        return logDir;
    }

    private PatternLayout createPatternLayout() {
        return PatternLayout.newBuilder()
                .withPattern("%d{yyyy-MM-dd'T'HH:mm:ss.SSSZ}  %-5level %pid --- [%t] [%logger] : %msg%n")
                .build();
    }

    private RollingFileAppender createRollingFileAppender(String logDir, PatternLayout layout) {
        return RollingFileAppender.newBuilder()
                .setName("RollingFileAppender")
                .withFileName(logDir + "/app.log")
                .withFilePattern(logDir + "/app-%d{yyyy-MM-dd}.%i.log.gz") // Compressed logs
                .setLayout(layout)
                .withPolicy(CompositeTriggeringPolicy.createPolicy(
                        SizeBasedTriggeringPolicy.createPolicy("10MB"),
                        TimeBasedTriggeringPolicy.newBuilder().withInterval(1).withModulate(true).build()
                ))
                .build();
    }

    private ConsoleAppender createConsoleAppender(PatternLayout layout) {
        return ConsoleAppender.newBuilder()
                .setName("ConsoleAppender")
                .setLayout(layout)
                .build();
    }

    private void configureLogger(Configuration config, String loggerName, Level level, RollingFileAppender appender) {
        LoggerConfig loggerConfig = config.getLoggerConfig(loggerName);
        if (!loggerConfig.getName().equals(loggerName)) {
            loggerConfig = new LoggerConfig(loggerName, level, true);
            config.addLogger(loggerName, loggerConfig);
        }
        loggerConfig.setLevel(level);
        loggerConfig.addAppender(appender, null, null);
    }
}
