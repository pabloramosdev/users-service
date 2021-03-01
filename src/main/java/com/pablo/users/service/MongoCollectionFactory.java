package com.pablo.users.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.pablo.users.domain.User;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Factory
public class MongoCollectionFactory {

    private final MongoClient mongoClient;

    @Inject
    public MongoCollectionFactory(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Bean
    @Singleton
    @Named("usersCollection")
    public MongoCollection<User> usersCollection() {
        return mongoClient.getDatabase("bank").getCollection("users", User.class);
    }

}
