package com.pablo.users.config.handler;

import com.pablo.users.exception.UserDoesNotExistsException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {UserDoesNotExistsException.class, ExceptionHandler.class})
public class NotFoundExceptionHandler implements ExceptionHandler<UserDoesNotExistsException, HttpResponse<Void>> {
    @Override
    public HttpResponse<Void> handle(HttpRequest request, UserDoesNotExistsException exception) {
        return HttpResponse.notFound();
    }
}
