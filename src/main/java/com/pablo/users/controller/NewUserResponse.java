package com.pablo.users.controller;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Introspected
public class NewUserResponse {

    private String id;
    private String name;
    private String lastName;
    private String phone;

}
