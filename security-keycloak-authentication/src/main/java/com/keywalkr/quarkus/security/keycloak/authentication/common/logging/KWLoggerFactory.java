package com.keywalkr.quarkus.security.keycloak.authentication.common.logging;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class KWLoggerFactory {

    @Produces
    public KWLogger createLogger(InjectionPoint injectionPoint){
        return KWLogger.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
