package com.pablo.users.controller;

import com.pablo.users.domain.Contact;
import com.pablo.users.domain.User;
import com.pablo.users.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller("/users")
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Post
    public HttpResponse<User> create(@Body @Valid User user) {
        userService.insert(user);
        return HttpResponse.created(user);
    }

    @Patch("/{userId}")
    public HttpResponse<User> update(String userId, @Body @Valid List<Contact> contacts) {
        return HttpResponse.created(userService.upsertContacts(userId, contacts));
    }

    @Get("/{userId}/contacts")
    public HttpResponse<List<Contact>> getContacts(String userId) {
        return HttpResponse.ok(userService.contactList(userId));
    }

    @Get("/commonContacts")
    public HttpResponse<Set<Contact>> getContacts(String userId1, String userId2) {
        return HttpResponse.ok(userService.commonsContacts(userId1, userId2));
    }

}
