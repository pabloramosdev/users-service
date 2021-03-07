package com.pablo.users.domain;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class Contact {

    @NotBlank
    @Size(min = 3, max = 30)
    private String contactName;
    @Pattern(regexp = "^[0-9]{9}$")
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
