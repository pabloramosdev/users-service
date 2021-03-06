package com.pablo.users.config.handler;

import com.pablo.users.exception.PhoneRegisteredException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {PhoneRegisteredException.class, ExceptionHandler.class})
public class BadRequestExceptionHandler implements ExceptionHandler<PhoneRegisteredException, HttpResponse<Void>> {
    @Override
    public HttpResponse<Void> handle(HttpRequest request, PhoneRegisteredException exception) {
        return HttpResponse.badRequest();
    }
}
