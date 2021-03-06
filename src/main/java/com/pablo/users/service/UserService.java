package com.pablo.users.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.InsertOneResult;
import com.pablo.users.controller.NewUserRequest;
import com.pablo.users.controller.NewUserResponse;
import com.pablo.users.domain.Contact;
import com.pablo.users.domain.User;
import com.pablo.users.exception.PhoneRegisteredException;
import com.pablo.users.exception.UserDoesNotExistsException;
import org.bson.BsonObjectId;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class UserService {

    private final MongoCollection<User> usersCollection;

    @Inject
    public UserService(@Named("usersCollection") MongoCollection<User> usersCollection) {
        this.usersCollection = usersCollection;
    }

    public User findById(String userId) {
        User user = usersCollection.find(Filters.eq("_id", new ObjectId((userId)))).first();
        if (user == null) {
            throw new UserDoesNotExistsException();
        }
        return user;
    }

    public NewUserResponse insert(@Valid NewUserRequest newUserRequest) {
        User existingUserWithThisPhone = usersCollection.find(Filters.eq("phone", newUserRequest.getPhone())).first();
        if (existingUserWithThisPhone != null) {
            throw new PhoneRegisteredException();
        }
        User newUser = new User(newUserRequest.getName(), newUserRequest.getLastName(), newUserRequest.getPhone());
        InsertOneResult insertOneResult = usersCollection.insertOne(newUser);
        BsonValue insertedId = insertOneResult.getInsertedId();
        BsonObjectId objectId = Objects.requireNonNull(insertedId).asObjectId();
        String newUserId = objectId.getValue().toHexString();
        return new NewUserResponse(newUserId, newUser.getName(), newUser.getLastName(), newUser.getPhone());
    }

    public User upsertContacts(String userId, List<Contact> contacts) {
        Bson filter = Filters.eq("_id", new ObjectId(userId));
        FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();
        findOneAndUpdateOptions.returnDocument(ReturnDocument.AFTER);
        findOneAndUpdateOptions.upsert(true);
        User updatedUser = usersCollection.findOneAndUpdate(filter, Updates.set("contacts", contacts), findOneAndUpdateOptions);
        return Optional.ofNullable(updatedUser).orElseThrow(UserDoesNotExistsException::new);
    }

    public List<Contact> contactList(String userId) {
        return findById(userId).getContacts();
    }

    public Set<Contact> commonsContacts(String userId1, String userId2) {
        List<Contact> user1Contacts = findById(userId1).getContacts();
        List<Contact> user2Contacts = findById(userId2).getContacts();
        return user1Contacts.stream().filter(user2Contacts::contains).collect(Collectors.toSet());
    }

}
