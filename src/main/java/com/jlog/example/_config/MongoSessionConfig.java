package com.jlog.example._config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.session.data.mongo.JacksonMongoSessionConverter;

import java.io.IOException;

//@Configuration
//@EnableReactiveMongoRepositories(basePackages = "org.springframework.session.data.mongo", reactiveMongoTemplateRef="mongoSessionTemplate")
public class MongoSessionConfig {

//    @Bean
//    public JacksonMongoSessionConverter mongoSessionConverter() {
//        return new JacksonMongoSessionConverter();
//    }
//
//    /**
//     * for embedded mongodb
//     */
//    @Bean
//    public MongoTemplate mongoSessionTemplate() throws IOException {
//        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
//        mongo.setBindIp("localhost");
//        MongoClient mongoClient = mongo.getObject();
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "session");
//        return mongoTemplate;
//    }
}
