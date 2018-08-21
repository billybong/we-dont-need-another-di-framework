package se.billy;

import java.net.URL;
import java.util.List;
import java.util.Properties;

public class Config {

    public final List<String> users;
    public final URL gitHubBaseUrl;

    private Config(Properties properties) {
        try {
            users = List.of("Billy", "Maria");
            gitHubBaseUrl = new URL(properties.getProperty("gitUrl"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Bootstrapping config failed", e);
        }
    }

    public static Config fromSystemProperties(){
        return new Config(System.getProperties());
    }
}
