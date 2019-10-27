package com.jlog.example.security.oauth2.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import reactor.core.publisher.Mono;

public class OAuth2AuthorizedClientService implements ReactiveOAuth2AuthorizedClientService {

    @Autowired private ReactiveClientRegistrationRepository clientRegistrationRepository;
    @Autowired private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> Mono<T> loadAuthorizedClient(String clientRegistrationId, String principalName) {
//        return (Mono<T>) Mono.zip(
//                clientRegistrationRepository.findByRegistrationId(clientRegistrationId),
//                contextRepository.findByRegistrationIdAndPrincipalName(clientRegistrationId, principalName),
//                (cr, ctx) -> new OAuth2AuthorizedClient(
//                        cr,
//                        ctx.getPrincipalName(),
//                        new OAuth2AccessToken(
//                                OAuth2AccessToken.TokenType.BEARER,
//                                ctx.getAuthorizedClient().getAccessToken().getTokenValue(),
//                                ctx.getAuthorizedClient().getAccessToken().getIssuedAt(),
//                                ctx.getAuthorizedClient().getAccessToken().getExpiresAt(),
//                                ctx.getAuthorizedClient().getAccessToken().getScopes()
//                        ),
//                        ctx.getAuthorizedClient().getRefreshToken() == null ? null : new OAuth2RefreshToken(
//                                ctx.getAuthorizedClient().getRefreshToken().getTokenValue(),
//                                ctx.getAuthorizedClient().getRefreshToken().getIssuedAt())
//                ))
//                .flatMap(ctx -> Mono.justOrEmpty(ctx))
//                .onErrorResume(e -> Mono.empty());
        return null;
    }

    @Override
    public Mono<Void> saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        return null;
    }

    @Override
    public Mono<Void> removeAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }
}
