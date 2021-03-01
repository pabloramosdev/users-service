package com.pablo.users.domain;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Introspected
public class Contact {

    @NotBlank
    private String contactName;
    @NotBlank
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return getPhone().equals(contact.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhone());
    }
}
