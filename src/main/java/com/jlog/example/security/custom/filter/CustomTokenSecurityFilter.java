package com.jlog.example.security.custom.filter;

import com.jlog.example.security.custom.token.CustomTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
public class CustomTokenSecurityFilter implements WebFilter {

    private CustomTokenProvider customTokenProvider;

    public CustomTokenSecurityFilter(CustomTokenProvider customTokenProvider) {
        this.customTokenProvider = customTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        try {
            String jwt = getJwtFromRequest(exchange.getRequest());

            if (StringUtils.hasText(jwt) && customTokenProvider.validateToken(jwt)) {
                String userId = customTokenProvider.getUserIdFromToken(jwt);

                UserDetails user = new User(userId, "dummy", Collections.emptyList());

                return chain
                        .filter(exchange)
                        .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())));
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        return chain.filter(exchange);
    }

    String getJwtFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }

        HttpCookie cookieToken = request.getCookies().getFirst("AUTH_TOKEN");
        if (cookieToken != null) {
            return cookieToken.getValue();
        }

        return null;
    }
}
