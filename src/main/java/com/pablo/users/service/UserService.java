package com.pablo.users.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.pablo.users.client.NeutrinoClient;
import com.pablo.users.client.PhoneInfo;
import com.pablo.users.domain.Contact;
import com.pablo.users.domain.User;
import com.pablo.users.service.exception.InvalidPhoneException;
import com.pablo.users.service.exception.PhoneRegisteredException;
import com.pablo.users.service.exception.UserDoesNotExistsException;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class UserService {

    private final MongoCollection<User> usersCollection;
    private final NeutrinoClient neutrinoClient;

    @Inject
    public UserService(@Named("usersCollection") MongoCollection<User> usersCollection,
                       NeutrinoClient neutrinoClient) {
        this.usersCollection = usersCollection;
        this.neutrinoClient = neutrinoClient;
    }

    public User findById(String userId) {
        User user = usersCollection.find(Filters.eq("_id", new ObjectId((userId)))).first();
        if (user == null) {
            throw new UserDoesNotExistsException();
        }
        return user;
    }

    public void insert(User user) {
        validateUserPhone(user.getPhone());
        User existingUser = usersCollection.find(Filters.eq("phone", user.getPhone())).first();
        if (existingUser != null) {
            throw new PhoneRegisteredException();
        }
        usersCollection.insertOne(user);
    }

    public User upsertContacts(String userId, List<Contact> contacts) {
        findById(userId);
        Bson filter = Filters.eq("_id", new ObjectId(userId));
        FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();
        findOneAndUpdateOptions.returnDocument(ReturnDocument.AFTER);
        return usersCollection.findOneAndUpdate(filter,
                Updates.addEachToSet("contacts", contacts), findOneAndUpdateOptions);
    }

    public List<Contact> contactList(String userId) {
        return findById(userId).getContacts();
    }

    public Set<Contact> commonsContacts(String userId1, String userId2) {
        List<Contact> user1Contacts = findById(userId1).getContacts();
        List<Contact> user2Contacts = findById(userId2).getContacts();
        return user1Contacts.stream().distinct().filter(user2Contacts::contains).collect(Collectors.toSet());
    }

    private void validateUserPhone(String phone) {
        PhoneInfo phoneInfo = neutrinoClient.validatePhone(phone);
        if (phoneInfo != null && !phoneInfo.getValid()) {
            throw new InvalidPhoneException();
        }
    }

}
