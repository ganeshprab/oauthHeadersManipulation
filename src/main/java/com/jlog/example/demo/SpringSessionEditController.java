package com.jlog.example.demo;

import com.jlog.example.security.oauth2.client.OAuth2AuthorizedClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
public class SpringSessionEditController {

    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @GetMapping("/save")
    public Mono<OAuth2AuthorizedClient> saveAuthorizedClient() {
        return oAuth2AuthorizedClientRepository.save(
                new OAuth2AuthorizedClient(
                        ClientRegistration
                                .withRegistrationId("facebook")
                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                .clientId("fClientId")
                                .clientName("fClientName")
                                .clientSecret("fClientSecret")
                                .redirectUriTemplate("http://redirect.test")
                                .authorizationUri("http://auth.test")
                                .tokenUri("http://token.test")
                                .userInfoAuthenticationMethod(AuthenticationMethod.FORM)
                                .userInfoUri("http://user.test")
                                .scope("testScope")
                                .build(),
                        "testPrincipal",
                        new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "testToken", Instant.now(), Instant.now())
                ));
    }
}
