package se.billy.infra.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import se.billy.infra.logging.Loggable;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class JsonApiClient implements Loggable {
    private final ObjectMapper om;
    private final AsyncHttpClient httpClient;

    public JsonApiClient(ObjectMapper om, AsyncHttpClient httpClient) {
        this.om = om;
        this.httpClient = httpClient;
    }

    public <T> CompletableFuture<Optional<T>> fetch(String uri, Class<T> clazz) {
        Request req = new RequestBuilder().setUrl(uri).build();
        long start = System.currentTimeMillis();
        log("Requesting " + clazz.getName());

        return httpClient.executeRequest(req).toCompletableFuture()
                .thenApply(response -> {
                    log("Response for " + clazz.getName() + " took " + (System.currentTimeMillis() - start) + " ms");
                    if (response.getStatusCode() == 404) {
                        return Optional.empty();
                    } else {
                        return Optional.of(this.parse(response, clazz));
                    }
                });
    }

    private <T> T parse(Response response, Class<T> clazz) {
        try {
            String responseBody = response.getResponseBody();
            return om.readValue(responseBody, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}