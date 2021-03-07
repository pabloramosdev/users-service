package com.pablo.users.controller;

import com.pablo.users.domain.Contact;
import com.pablo.users.domain.User;
import com.pablo.users.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
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
    public HttpResponse<NewUserResponse> create(@Body @Valid NewUserRequest newUserRequest) {
        return HttpResponse.created(userService.insert(newUserRequest));
    }

    @Patch("/{userId}")
    public HttpResponse<User> update(@Pattern(regexp = "[0-9a-fA-F]{24}") String userId,
                                     @Body @Valid List<Contact> contacts) {
        return HttpResponse.ok(userService.upsertContacts(userId, contacts));
    }

    @Get("/{userId}/contacts")
    public HttpResponse<List<Contact>> getContacts(@Pattern(regexp = "[0-9a-fA-F]{24}") String userId) {
        return HttpResponse.ok(userService.contactList(userId));
    }

    @Get("/commonContacts")
    public HttpResponse<Set<Contact>> getCommonContacts(@Pattern(regexp = "[0-9a-fA-F]{24}") String userId1,
                                                        @Pattern(regexp = "[0-9a-fA-F]{24}") String userId2) {
        return HttpResponse.ok(userService.commonsContacts(userId1, userId2));
    }

}
