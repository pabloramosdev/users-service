package com.pablo.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Introspected
public class User {

    @JsonIgnore
    private ObjectId id;
    private String name;
    private String lastName;
    private String phone;
    private List<Contact> contacts = new ArrayList<>();

    public User(String name, String lastName, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }

}