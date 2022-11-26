package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input;

import com.keywalkr.commons.logger.KWLogger;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationRequestDto;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model.AuthenticationResponseDto;
import com.keywalkr.quarkus.security.keycloak.authentication.domain.usecase.AuthencicationUseCase;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("secure")
public class AuthenticationResource {

    @Inject
    KWLogger logger;

    @Inject
    AuthencicationUseCase authencicationUseCase;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthenticationResponseDto authenticate(@Valid AuthenticationRequestDto authenticationRequestDto) {
        logger.info("User {0} authenticates to {1}", authenticationRequestDto.getUsername(), authenticationRequestDto.getClientId());
        return authencicationUseCase.authenticate(authenticationRequestDto);
    }

    public void logout(){

    }
}
