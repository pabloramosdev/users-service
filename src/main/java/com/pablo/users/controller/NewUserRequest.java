package com.pablo.users.controller;

import com.pablo.users.config.validation.PhoneValidation;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Introspected
public class NewUserRequest {

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;
    @NotBlank
    @Size(min = 3, max = 30)
    private String lastName;
    @PhoneValidation
    private String phone;

}
