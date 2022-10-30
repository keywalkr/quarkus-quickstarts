package com.keywalkr.quarkus.security.keycloak.authentication.domain.usecase;

import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationRequestDto;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationResponseDto;

public interface AuthencicationUseCase {

    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
}
