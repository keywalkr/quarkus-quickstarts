package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class KeycloakAuthenticateRequest {

    private String username;
    private String password;
    private String client_id;
    private String scope;
    private String grant_type;
}
