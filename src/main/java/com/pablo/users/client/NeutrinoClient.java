package com.pablo.users.client;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;

@Singleton
public class NeutrinoClient {

    @Value("${neutrino.userId}")
    private String neutrinoUserId;
    @Value("${neutrino.apiKey}")
    private String neutrinoApiKey;

    private final RxHttpClient httpClient;

    @Inject
    public NeutrinoClient(@Client("${neutrino.url}") RxHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public PhoneInfo validatePhone(String phone) {
        URI phoneValidationUri = UriBuilder.of("/phone-validate").queryParam("number", phone)
                .queryParam("country-code","ES")
                .queryParam("output-case", "camel").build();
        return httpClient.toBlocking().retrieve(HttpRequest.GET(phoneValidationUri)
                .header("user-id", neutrinoUserId)
                .header("api-key", neutrinoApiKey), PhoneInfo.class);
    }

}
