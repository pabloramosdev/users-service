package com.pablo.users.config.validation;

import com.pablo.users.client.NeutrinoClient;
import com.pablo.users.client.PhoneInfo;
import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Factory
public class ValidationFactory {

    private final NeutrinoClient neutrinoClient;

    @Inject
    public ValidationFactory(NeutrinoClient neutrinoClient) {
        this.neutrinoClient = neutrinoClient;
    }

    @Singleton
    ConstraintValidator<PhoneValidation, String> phoneValidator() {
        return (value, annotationMetadata, context) ->
                value != null && value.matches("^[0-9]{9}$") && neutrinoValidation(value);
    }

    private boolean neutrinoValidation(String phone) {
        PhoneInfo phoneInfo = neutrinoClient.validatePhone(phone);
        return phoneInfo != null && phoneInfo.getValid();
    }
}
