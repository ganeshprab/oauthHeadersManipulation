package com.jlog.example.security.oauth2.client;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import reactor.core.publisher.Mono;

public interface OAuth2AuthorizedClientRepository extends ReactiveMongoRepository<OAuth2AuthorizedClient, String> {
}
