package com.pablo.users.service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    private MongoCollection usersCollection;

    @BeforeEach
    void setUp() {
        usersCollection = Mockito.mock(MongoCollection.class);
        userService = new UserService(usersCollection);
    }

    @Test
    @DisplayName("given user id when find user by id then return the user")
    void findById() {
        FindIterable findIterable = Mockito.mock(FindIterable.class);
        when(usersCollection.find(any(Bson.class))).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(new User());
        User response = userService.findById("6042591fe2cbcc2e6ce612b9");
        verify(usersCollection).find(any(Bson.class));
        assertNotNull(response);
    }

    @Test
    @DisplayName("given bad userId when find user by id then return throws UserDoesNotExistsException")
    void findByIdException() {
        FindIterable findIterable = Mockito.mock(FindIterable.class);
        when(usersCollection.find(any(Bson.class))).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(null);
        assertThrows(UserDoesNotExistsException.class, () -> userService.findById("6042591fe2cbcc2e6ce612b9"));
    }

    @Test
    @DisplayName("given new user when insert it then return the user created")
    void insert() {
        FindIterable findIterable = Mockito.mock(FindIterable.class);
        InsertOneResult insertOneResult = Mockito.mock(InsertOneResult.class);
        BsonValue insertedId = mock(BsonValue.class);
        BsonObjectId bsonObjectId = mock(BsonObjectId.class);

        when(usersCollection.find(any(Bson.class))).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(null);
        when(usersCollection.insertOne(any())).thenReturn(insertOneResult);
        when(insertOneResult.getInsertedId()).thenReturn(insertedId);
        when(insertedId.asObjectId()).thenReturn(bsonObjectId);
        when(bsonObjectId.getValue()).thenReturn(new ObjectId());

        NewUserResponse response = userService.insert(new NewUserRequest());

        verify(usersCollection).find(any(Bson.class));
        verify(usersCollection).insertOne(any());

        assertNotNull(response);
    }

    @Test
    @DisplayName("given new user with existing phone when insert it then throws PhoneRegisteredException")
    void insertKO() {
        FindIterable findIterable = Mockito.mock(FindIterable.class);

        when(usersCollection.find(any(Bson.class))).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(new User());

        assertThrows(PhoneRegisteredException.class, () -> userService.insert(new NewUserRequest()));
    }

    @Test
    @DisplayName("given user id and a contact list when upsert it then return updated user")
    void upsertContacts() {
        when(usersCollection.findOneAndUpdate(any(Bson.class), any(Bson.class), any(FindOneAndUpdateOptions.class)))
                .thenReturn(new User());
        userService.upsertContacts("6042591fe2cbcc2e6ce612b9", Collections.emptyList());
        verify(usersCollection).findOneAndUpdate(any(Bson.class), any(Bson.class), any(FindOneAndUpdateOptions.class));
    }

    @Test
    @DisplayName("given non existing user id and contact list when upsert ir then throws UserDoesNotExistsException")
    void upsertContactsKO() {
        when(usersCollection.findOneAndUpdate(any(Bson.class), any(Bson.class), any(FindOneAndUpdateOptions.class)))
                .thenReturn(null);
        assertThrows(UserDoesNotExistsException.class, () -> userService.upsertContacts("6042591fe2cbcc2e6ce612b9", Collections.emptyList()));
    }

    @Test
    @DisplayName("given user id when search contact list then return contact list othe user")
    void contactList() {
        FindIterable findIterable = Mockito.mock(FindIterable.class);
        when(usersCollection.find(any(Bson.class))).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(new User());

        List<Contact> response = userService.contactList("6042591fe2cbcc2e6ce612b9");

        verify(usersCollection).find(any(Bson.class));
        assertNotNull(response);
    }

    @Test
    @DisplayName("given userId1 and userId2 when search common contacts then return common contact list")
    void commonsContacts() {
        FindIterable findIterable = Mockito.mock(FindIterable.class);
        when(usersCollection.find(any(Bson.class))).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(new User());

        userService.commonsContacts("6042591fe2cbcc2e6ce612b9", "604164ed1c572f6ee1b05287");

        verify(usersCollection, times(2)).find(any(Bson.class));
    }
}