package com.pablo.users.domain;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@Introspected
public class Contact {

    private String contactName;
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return getContactName().equals(contact.getContactName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhone());
    }
}
