package com.jlog.example._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.reactive.EnableMongoWebSession;

import java.time.Duration;

@Configuration
@EnableMongoWebSession(collectionName = "sessions")
public class MongoSessionConfig {

    @Bean
    public JdkMongoSessionConverter jdkMongoSessionConverter() {
        return new JdkMongoSessionConverter(Duration.ofMinutes(30));
    };
}
