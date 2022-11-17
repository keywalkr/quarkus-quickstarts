package com.keywalkr.commons.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class KeywalkrLogger {

    private Logger logger;

    public static KeywalkrLogger getLogger(Class clazz){
        return new KeywalkrLogger(LoggerFactory.getLogger(clazz));
    }

    private KeywalkrLogger(Logger logger) {
        this.logger = logger;
    }

    public void info(String pattern, Object... args){
        Objects.requireNonNull(pattern);
        logMessage(logger::info, Optional.of(pattern), args);
    }

    private void logMessage(Consumer<String> consumer, Optional<String> pattern, Object... args){
        String message = pattern.map(m -> MessageFormat.format(m, args)).orElse(null);
        consumer.accept(message);
    }
}
