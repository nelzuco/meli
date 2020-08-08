package com.meli.iplookup.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ExternalApiErrorHandler implements ResponseErrorHandler {


    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return HttpStatus.OK != clientHttpResponse.getStatusCode();
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException, HttpClientErrorException {
        throw new HttpClientErrorException(clientHttpResponse.getStatusCode(), clientHttpResponse.getStatusText());
    }
}
