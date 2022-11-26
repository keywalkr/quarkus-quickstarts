package com.keywalkr.quarkus.security.keycloak.authentication.domain.usecase.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.mapping.DtoMapper;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationRequestDto;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationResponseDto;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.KeycloakClient;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model.KeycloakAuthenticateRequest;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model.KeycloakAuthenticateResponse;
import com.keywalkr.quarkus.security.keycloak.authentication.domain.usecase.AuthencicationUseCase;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.Map;

@ApplicationScoped
public class AuthenticationUseCaseImpl implements AuthencicationUseCase {

    @Inject
    @RestClient
    KeycloakClient keycloakClient;

    @Inject
    DtoMapper dtoMapper;

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {

        ObjectMapper objectMapper = new ObjectMapper();
        KeycloakAuthenticateRequest keycloakAuthenticateRequest = dtoMapper.toKeycloakAuthenticateRequest(request).toBuilder()
                .scope("openid")
                .grant_type("password")
                .build();
        Map<String, Object> requestMap = objectMapper.convertValue(keycloakAuthenticateRequest, new TypeReference<Map<String, Object>>() {
        });

        KeycloakAuthenticateResponse keycloakAuthenticateResponse = keycloakClient.authenticate(new MultivaluedHashMap<>(requestMap));


        //keycloakClient.userInfo();

        return dtoMapper.toAuthenticationResponseDto(keycloakAuthenticateResponse);
    }
}
