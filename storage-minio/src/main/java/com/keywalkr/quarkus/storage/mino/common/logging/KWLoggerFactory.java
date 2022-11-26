package com.keywalkr.quarkus.storage.mino.common.logging;

import com.keywalkr.commons.logger.KWLogger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class KWLoggerFactory {

    @Produces
    public KWLogger createLogger(InjectionPoint injectionPoint) {
        return KWLogger.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
