package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthenticationResponseDto {
    private String access_token;
    private int expires_in;
    private int refresh_expires_in;
    private String refresh_token;
    private String token_type;
    private String id_token;
    private String session_state;


}
