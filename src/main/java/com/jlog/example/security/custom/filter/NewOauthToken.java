package com.jlog.example.security.custom.filter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Collection;

public class NewOauthToken extends OAuth2AuthenticationToken {
    public NewOauthToken(OAuth2User principal, Collection<? extends GrantedAuthority> authorities, String authorizedClientRegistrationId) {
        super(principal, authorities, authorizedClientRegistrationId);
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities(){
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_OFFICER"));
    }
}
