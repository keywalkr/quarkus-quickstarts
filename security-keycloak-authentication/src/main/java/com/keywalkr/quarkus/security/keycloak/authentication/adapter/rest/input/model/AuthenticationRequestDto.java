package com.keywalkr.quarkus.security.keycloak.authentication.adapter.rest.input.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthenticationRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String clientId;
}
