package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client;

import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model.KeycloakAuthenticateResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

@Path("/realms/development/protocol/openid-connect")
@RegisterRestClient(configKey = "keycloak_client")
public interface KeycloakClient {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    KeycloakAuthenticateResponse authenticate(MultivaluedMap<String, ?> params);


}
