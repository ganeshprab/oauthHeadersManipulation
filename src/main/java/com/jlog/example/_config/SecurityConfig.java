package com.jlog.example._config;

import com.jlog.example.security.custom.filter.BypassAuthentication;
import com.jlog.example.security.custom.filter.BypassPrincipal;
import com.jlog.example.security.custom.filter.CustomTokenSecurityFilter;
import com.jlog.example.security.custom.token.CustomTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class SecurityConfig {

    @Bean
    public CustomTokenProvider customTokenProvider() {
        return new CustomTokenProvider(Duration.of(1, ChronoUnit.DAYS), "abcd");
    }

    /**
     * spring oauth2 와 actuator 의 security 가 충돌나는 부분이 있어 custom SecurityWebFilterChain 을 구현함
     */
    /*@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.addFilterBefore(new CustomTokenSecurityFilter(customTokenProvider()), SecurityWebFiltersOrder.OAUTH2_AUTHORIZATION_CODE);

        // @formatter:off
        return http
                .authorizeExchange()
                    .anyExchange().authenticated()
                .and()
                .oauth2Login()
                    .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler() {
                        @Override
                        public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                            webFilterExchange.getExchange().getResponse()
                                    .addCookie(ResponseCookie.from("AUTH_TOKEN", customTokenProvider().createToken(authentication)).path("/")
                                            .httpOnly(true)
                                            .maxAge(Duration.ofMinutes(30))
                                            .build());
                            return super.onAuthenticationSuccess(webFilterExchange, authentication);
                        }
                    })
                    .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/login"))
                .and()
                .build();
        // @formatter:on
    }*/
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.addFilterBefore(new CustomTokenSecurityFilter(customTokenProvider()), SecurityWebFiltersOrder.OAUTH2_AUTHORIZATION_CODE);

        // @formatter:off
        return http
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler() {
                    @Override
                    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                        webFilterExchange.getExchange().getResponse()
                                .addCookie(ResponseCookie.from("AUTH_TOKEN", customTokenProvider().createToken(authentication)).path("/")
                                        .httpOnly(true)
                                        .maxAge(Duration.ofMinutes(30))
                                        .build());
                        BypassPrincipal bypassPrincipal = new BypassPrincipal("test123", "test124");
                        BypassAuthentication bypassAuthentication = new BypassAuthentication(bypassPrincipal);
                        webFilterExchange.getChain()
                                .filter(webFilterExchange.getExchange())
                                .subscriberContext(ReactiveSecurityContextHolder.withAuthentication(bypassAuthentication));
                        return super.onAuthenticationSuccess(webFilterExchange, authentication);
                    }
                })
                .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/login"))
                .and()
                .build();
        // @formatter:on
    }
}
