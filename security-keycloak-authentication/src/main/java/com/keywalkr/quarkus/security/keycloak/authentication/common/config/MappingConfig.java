package com.keywalkr.quarkus.security.keycloak.authentication.common.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public class MappingConfig {
}
