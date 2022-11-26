package com.keywalkr.commons.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

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

    public void error(String message, Throwable exception) {
        requireNonNull(exception);
        logMessage(m-> log.error(m, exception), message);
    }

    public void trace(String pattern, Object... args){
        logMessage(log::trace, pattern, args);
    }

    private void logMessage(Consumer<String> messageCosumer, String pattern, Object... args){
        requireNonNull(pattern);
        String message = Optional.of(pattern).map(p ->"Message: " +  MessageFormat.format(p, args)).orElse(null);
        messageCosumer.accept(message);
    }
}
