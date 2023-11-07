package com.jlog.example.security.custom.filter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class BypassAuthentication extends OAuth2AuthenticationToken implements Serializable {

    private static final long serialVersionUID = 1L;
    private final OAuth2User authenticatedPrincipal;
    public BypassAuthentication(OAuth2User principal) {
        super(principal, Collections.singleton(new SimpleGrantedAuthority("ROLE_OFFICER")), "client_reg_id");
        this.authenticatedPrincipal = principal;
    }
    @Override
    public String getName(){
        return authenticatedPrincipal.getName();
    }
}
