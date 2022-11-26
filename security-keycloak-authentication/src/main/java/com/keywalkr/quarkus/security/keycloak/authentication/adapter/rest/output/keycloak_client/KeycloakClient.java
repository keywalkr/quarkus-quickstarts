package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client;

import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model.KeycloakAuthenticateResponse;
import com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model.UserInfo;
import com.keywalkr.quarkus.security.keycloak.authentication.common.config.KeycloakClientHeader;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

@Path("/realms/development/protocol/openid-connect")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "keycloak-client")
@RegisterClientHeaders(KeycloakClientHeader.class)
public interface KeycloakClient {

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    KeycloakAuthenticateResponse authenticate(MultivaluedMap<String, ?> params);

    @GET
    @Path("/userinfo")
    UserInfo userInfo();
}
