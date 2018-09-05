package se.billy.delivery.aws;

import com.amazonaws.services.lambda.runtime.Context;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldEndpoint {
    public AwsHttpResponse<Map<String, String>> hello(Object input, Context context) {
        HashMap<String, String> response = new HashMap<>();
        response.put("hello", "world");
        return AwsHttpResponse.ok(response);
    }

    public AwsHttpResponse<Map<String, String>> bye(Object input, Context context) {
        HashMap<String, String> response = new HashMap<>();
        response.put("bye", "world");
        return AwsHttpResponse.ok(response);
    }
}
