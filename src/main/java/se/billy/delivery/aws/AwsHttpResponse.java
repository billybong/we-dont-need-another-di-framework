package se.billy.delivery.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public final class AwsHttpResponse<T> {
    private static final ObjectMapper om = new ObjectMapper();

    public final int statusCode;
    public final Map<String, String> headers = new HashMap<>();
    public final String body;
    public final boolean isBase64Encoded = false;

    private AwsHttpResponse(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        headers.put("Content-Type", "application/json");
    }

    public static <T> AwsHttpResponse<T> ok(T body){
        return withStatus(body, 200);
    }

    public static <T> AwsHttpResponse<T> error(T body){
        return withStatus(body, 503);
    }

    public static <T> AwsHttpResponse<T> withStatus(T body, int status){
        try {
            String json = om.writeValueAsString(body);
            return new AwsHttpResponse<T>(json, status);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}