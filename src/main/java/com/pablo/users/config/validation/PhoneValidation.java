package com.pablo.users.config.validation;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {})
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface PhoneValidation {
    String message() default "Phone number is not a valid number.";
}
