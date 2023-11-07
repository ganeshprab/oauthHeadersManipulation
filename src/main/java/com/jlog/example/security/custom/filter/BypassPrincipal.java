package com.jlog.example.security.custom.filter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class BypassPrincipal implements OAuth2User, Serializable {
    private final String officerId;
    private final String scotiaId;

    private Map<String , Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public BypassPrincipal(String officerId, String scotiaId){
        this.officerId = officerId;
        this.scotiaId =scotiaId;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_OFFICER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return scotiaId;
    }
    @Override
    public String getAttribute(String name) {
        return (String)attributes.get(name);
    }
    @Override
    public Map<String , Object> getAttributes() {
        return attributes;
    }
}
