package com.keywalkr.quarkus.security.keycloak.authentication.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class KWLogger {

    private Logger log;

    public static KWLogger getLogger(Class clazz){
        return new KWLogger(LoggerFactory.getLogger(clazz));
    }

    private KWLogger(Logger logger){
        this.log = logger;
    }

    public void info(String pattern, Object... args){
        logMessage(log::info, pattern, args);
    }

    public void debug(String pattern, Object... args){
        logMessage(log::debug, pattern, args);
    }

    public void error(String pattern, Object... args){
        logMessage(log::error, pattern, args);
    }

    private void logMessage(Consumer<String> logLevel, String pattern, Object... args){
        Objects.requireNonNull(pattern);
        String message = Optional.of(pattern).map(p -> MessageFormat.format(p, args)).orElse(null);
        logLevel.accept(message);
    }
}
