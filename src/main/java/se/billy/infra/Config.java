package se.billy.infra;

import se.billy.infra.function.FunctionThrowingException;

import java.util.Map;
import java.util.Properties;

public class Config {
    public final String wikipediaUrl;
    public final String musicBrainzUrl;
    public final String coverArtUrl;
    public final int port;

    private Properties properties;

    private Config(Properties properties) {
        this.properties = properties;

        try {
            wikipediaUrl =      property("wikipediaUrl");
            musicBrainzUrl =    property("musicBrainzUrl");
            coverArtUrl =       property("coverArtUrl");
            port =              property("port", Integer::valueOf);
        } catch (Exception e) {
            throw new IllegalArgumentException("Bootstrapping config failed", e);
        }
    }

    public static Config fromSystemProperties(){
        return new Config(System.getProperties());
    }

    public static Config fromConfigFile() {
        Properties props = new Properties();

        props.putAll(Map.of(
                "wikipediaUrl", "https://en.wikipedia.org/w/api.php",
                "musicBrainzUrl", "http://musicbrainz.org",
                "coverArtUrl", "http://coverartarchive.org",
                "port", "8080"
        ));

        return new Config(props);
    }

    private String property(String name){
        return properties.getProperty(name);
    }

    private <T> T property(String name, FunctionThrowingException<String, T> factory){
        try {
            return factory.apply(property(name));
        }catch (Exception e){
            throw new IllegalArgumentException("Failed to load property " + name, e);
        }
    }
}