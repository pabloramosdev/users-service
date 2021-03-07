package com.pablo.users.controller;

import com.pablo.users.domain.Contact;
import com.pablo.users.domain.User;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class UserControllerIT {

    @Inject
    @Client("/")
    private RxHttpClient httpClient;

    @Test
    @DisplayName("given new user when create it then return HttpStatus CREATED and the user created")
    void create() {
        URI getContactsUri = UriBuilder.of("/users").build();

        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setName("USER3");
        newUserRequest.setLastName("LASTNAME3");
        newUserRequest.setPhone("913234781");

        HttpResponse<NewUserResponse> response = httpClient.toBlocking()
                .exchange(HttpRequest.POST(getContactsUri, newUserRequest), NewUserResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        NewUserResponse newUserResponse = response.body();

        assertNotNull(newUserResponse);
        assertEquals("USER3", newUserResponse.getName());
        assertEquals("LASTNAME3", newUserResponse.getLastName());
        assertEquals("913234781", newUserResponse.getPhone());

    }

    @Test
    @DisplayName("given userId and updating contacts when update it then return HttpStatus OK and the user updated")
    void update() {
        URI getContactsUri = UriBuilder.of("/users/{userId}").expand(Collections.singletonMap("userId","60443672a1d31c3a4617d90e"));

        List<Contact> updatingContacts =
                List.of(new Contact("contact1", "123456789"),
                        new Contact("contact3", "677854499"),
                        new Contact("contact4", "933457866"));

        HttpResponse<User> response = httpClient.toBlocking()
                .exchange(HttpRequest.PATCH(getContactsUri, updatingContacts), User.class);

        assertEquals(HttpStatus.OK, response.getStatus());

        User user = response.body();
        assertNotNull(user);

        List<Contact> updatedContacts = user.getContacts();

        assertNotNull(updatedContacts);
        assertEquals(3, updatedContacts.size());
        assertEquals("contact1", updatedContacts.get(0).getContactName());
        assertEquals("123456789", updatedContacts.get(0).getPhone());
        assertEquals("contact3", updatedContacts.get(1).getContactName());
        assertEquals("677854499", updatedContacts.get(1).getPhone());
        assertEquals("contact4", updatedContacts.get(2).getContactName());
        assertEquals("933457866", updatedContacts.get(2).getPhone());

    }

    @Test
    @DisplayName("given userId when get contacts then return HttpStatus OK and list of contacts")
    void getContacts() {
        URI getContactsUri = UriBuilder.of("/users/{userId}/contacts").expand(Collections.singletonMap("userId","60443672a1d31c3a4617d90d"));

        HttpResponse<List<Contact>> response = httpClient.toBlocking().exchange(HttpRequest.GET(getContactsUri),
                Argument.listOf(Contact.class));

        assertEquals(HttpStatus.OK, response.getStatus());

        List<Contact> contacts = response.body();

        assertNotNull(contacts);
        assertEquals(2, contacts.size());
        assertEquals("contact1", contacts.get(0).getContactName());
        assertEquals("123456789", contacts.get(0).getPhone());
        assertEquals("contact2", contacts.get(1).getContactName());
        assertEquals("987654321", contacts.get(1).getPhone());
    }

    @Test
    @DisplayName("given userId1 and userId2 when get common contacts then return HttpStatus OK and list of common contacts")
    void getCommonContacts() {
        URI getContactsUri = UriBuilder.of("/users/commonContacts")
                .queryParam("userId1", "60443672a1d31c3a4617d90d")
                .queryParam("userId2", "60443672a1d31c3a4617d90e").build();

        HttpResponse<List<Contact>> response = httpClient.toBlocking().exchange(HttpRequest.GET(getContactsUri),
                Argument.listOf(Contact.class));

        assertEquals(HttpStatus.OK, response.getStatus());

        List<Contact> contacts = response.body();

        assertNotNull(contacts);
        assertEquals(1, contacts.size());
        assertEquals("contact1", contacts.get(0).getContactName());
        assertEquals("123456789", contacts.get(0).getPhone());
    }
}