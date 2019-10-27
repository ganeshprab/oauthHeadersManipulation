package com.jlog.example._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * spring oauth2 와 actuator 의 security 가 충돌나는 부분이 있어 custom SecurityWebFilterChain 을 구현함
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // @formatter:off
        return http
                .authorizeExchange()
                .anyExchange().permitAll()
//                .pathMatchers("/actuator/**", "/demo**").permitAll()
//                .anyExchange().authenticated()
                .and()
//                .oauth2Login()
//                .and()
                .build();
        // @formatter:on
    }

//    /**
//     * Custom configuration of {@link org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration ReactiveOAuth2ClientAutoConfiguration}
//     */
//    @Bean
//    public ReactiveOAuth2AuthorizedClientService authorizedClientService() {
//        return new OAuth2AuthorizedClientService();
//    }
}
