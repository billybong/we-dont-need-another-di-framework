package se.billy.infra.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static se.billy.infra.function.ExceptionalFunctions.rethrowsAsRuntime;

public class JsonApiClient {
    private final ObjectMapper om;
    private final HttpClient httpClient;

    public JsonApiClient(ObjectMapper om, HttpClient httpClient) {
        this.om = om;
        this.httpClient = httpClient;
    }

    public <T> CompletableFuture<Optional<T>> fetch(String uriString, Class<T> returnType) {
        var uri = rethrowsAsRuntime(URI::create, RuntimeException::new).apply(uriString);
        var req = HttpRequest.newBuilder(uri).GET().build();

        return httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(response -> {
                    if(response.statusCode() == 404){
                        return Optional.empty();
                    }
                    validateResponse(response);
                    return Optional.of(parse(response.body(), returnType));
                });
    }

    private void validateResponse(HttpResponse<InputStream> response) {
        int statusCode = response.statusCode();
        if(statusCode > 399){
            throw new RuntimeException("Failed to query " + response.request().uri() + " status code: " + statusCode);
        }
    }

    private <T> T parse(InputStream body, Class<T> type) {
        try {
            return om.readValue(body, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}