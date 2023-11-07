package com.jlog.example.security.custom.filter;

import com.jlog.example.security.custom.token.CustomTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class CustomTokenSecurityFilter implements WebFilter {

    private ReactiveAuthorizationManager<? super ServerWebExchange> accessDecisionManager;
    private CustomTokenProvider customTokenProvider;

    public CustomTokenSecurityFilter( CustomTokenProvider customTokenProvider) {
        this.customTokenProvider = customTokenProvider;
    }

    //@Override
    public Mono<Void> filter1(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            return ReactiveSecurityContextHolder.getContext()
                    .map(c -> c.getAuthentication())
                    .map(c -> c.getAuthorities())
                    .flatMap(roles -> {
                        if(roles.contains("ROLE_OFFICER")){
                            UserDetails user = new User("yuu", "dummy", Collections.emptyList());
                            return chain
                                    .filter(exchange)
                                    .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())));

                        }else {
                            UserDetails user = new User("yuu", "dummy", Collections.emptyList());
                            return chain
                                    .filter(exchange)
                                    .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())));

                        }
                    });

            /*return exchange.getPrincipal().flatMap( principal ->
                    {
                        if(principal.getName() =="test"){
                            return chain.filter(exchange);
                        }else {
                            return exchange.getResponse().setComplete();
                        }
                    }
            );*/
            /*return ReactiveSecurityContextHolder.getContext()
                    .filter(c -> c.getAuthentication() != null)
                    .map(SecurityContext::getAuthentication)
                    .map(Authentication::getPrincipal)
                    .flatMap(authentication -> {
                        if(authentication ==null ){
                            return exchange.getResponse().setComplete();
                        }
                        else{
                            return chain.filter(exchange);
                        }
                    })
                    .switchIfEmpty(chain.filter(exchange));
            /*return ReactiveSecurityContextHolder.getContext()
                    .filter(c -> c.getAuthentication() == null)
                    .map(SecurityContext::getAuthentication)
                    .filter(c -> c.getPrincipal() == null)
                    .map(Authentication::getPrincipal)
                    .as(principal -> {
                        if("principal.subscribe() == null" == "kuhf"){
                            BypassPrincipal bypassPrincipal = new BypassPrincipal("test123", "test124");
                            BypassAuthentication bypassAuthentication = new BypassAuthentication(bypassPrincipal);
                            return chain
                                    .filter(exchange)
                                    .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(bypassAuthentication));
                        }else {
                            BypassPrincipal bypassPrincipal = new BypassPrincipal("notest123", "test124");
                            BypassAuthentication bypassAuthentication = new BypassAuthentication(bypassPrincipal);
                            return chain
                                    .filter(exchange)
                                    .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(bypassAuthentication));
                        }

                    })
                    .switchIfEmpty(chain
                            .filter(exchange));       /*
            Mono<String> a = ReactiveSecurityContextHolder.getContext()
                    .flatMap(securityContext -> {
                        if (securityContext != null) {

                            BypassPrincipal bypassPrincipal = new BypassPrincipal("test123", "test124");
                            BypassAuthentication bypassAuthentication = new BypassAuthentication(bypassPrincipal);
                            return Mono.just("test");
                            /*return chain
                                    .filter(exchange)
                                    .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(bypassAuthentication));
                        } else {
                            return Mono.just("untest");
                        }
                    });
            return Mono.zip(a, Mono.just("hhf")).flatMap(tuple -> {
                if (tuple.getT1() == "test") {
                    BypassPrincipal bypassPrincipal = new BypassPrincipal("test123", "test124");
                    BypassAuthentication bypassAuthentication = new BypassAuthentication(bypassPrincipal);
                    return chain
                            .filter(exchange)
                            .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(bypassAuthentication));
                }else {
                    return chain.filter(exchange);
                }
                });*/
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
            return chain.filter(exchange);
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        /*try {
            String jwt = getJwtFromRequest(exchange.getRequest());

            if (StringUtils.hasText(jwt) && customTokenProvider.validateToken(jwt)) {
                String userId = customTokenProvider.getUserIdFromToken(jwt);

                UserDetails user = new User("yuu", "dummy", Collections.emptyList());

                return chain
                        .filter(exchange)
                        .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())));
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }*/

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
