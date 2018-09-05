package se.billy.delivery.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public final class AwsHttpResponse {
    private static final ObjectMapper om = new ObjectMapper();
    private final int statusCode;
    private final Map<String, String> headers = new HashMap<>();
    private final String body;

    private AwsHttpResponse(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        headers.put("Content-Type", "application/json");
    }

    public static AwsHttpResponse ok(Object body){
        return withStatus(body, 200);
    }

    public static  AwsHttpResponse error(Object body){
        return withStatus(body, 503);
    }

    public static AwsHttpResponse withStatus(Object body, int status){
        try {
            String json = om.writeValueAsString(body);
            return new AwsHttpResponse(json, status);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean getIsBase64Encoded(){ return false;}
}
