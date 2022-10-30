package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.mapping;

import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationRequestDto;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationResponseDto;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model.KeycloakAuthenticateRequest;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model.KeycloakAuthenticateResponse;
import com.keywalkr.quarkus.security.keycloak.authentication.common.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface DtoMapper {

    @Mapping(source = "clientId", target = "client_id")
    KeycloakAuthenticateRequest toKeycloakAuthenticateRequest(AuthenticationRequestDto requestDto);

    AuthenticationResponseDto toAuthenticationResponseDto(KeycloakAuthenticateResponse keycloakAuthenticateResponse);
}
