package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.output.keycloak_client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String sub;
    private boolean email_verified;
    private String preferred_username;
    private String given_name;
    private String family_name;
    private String email;
}
