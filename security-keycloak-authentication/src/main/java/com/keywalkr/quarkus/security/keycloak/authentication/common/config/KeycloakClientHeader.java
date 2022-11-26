package com.keywalkr.quarkus.security.keycloak.authentication.common.config;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedMap;

@ApplicationScoped
public class KeycloakClientHeader implements ClientHeadersFactory {
    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incoming, MultivaluedMap<String, String> clientOutgoing) {

        MultivaluedMap<String, String> toRequest = new MultivaluedMapImpl<>();

        if(incoming.containsKey("Authorization")){
            toRequest.add("Auhtorization", incoming.get("Authorization").toString());
        }else {
            toRequest.add("grant_type", "password");
            toRequest.add("scope", "openid");
        }
        return toRequest;
    }
}
