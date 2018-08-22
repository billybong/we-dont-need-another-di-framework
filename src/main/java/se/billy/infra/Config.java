package se.billy.infra;

import se.billy.infra.function.FunctionThrowingException;

import java.util.Map;
import java.util.Properties;

public class Config {
    public final String wikipediaUrl;
    public final String musicBrainzUrl;
    public final String coverArtUrl;
    private Properties properties;

    private Config(Properties properties) {
        this.properties = properties;

        try {
            wikipediaUrl =      property("wikipediaUrl");
            musicBrainzUrl =    property("musicBrainzUrl");
            coverArtUrl =       property("coverArtUrl");
        } catch (Exception e) {
            throw new IllegalArgumentException("Bootstrapping config failed", e);
        }
    }

    public static Config fromSystemProperties(){
        return new Config(System.getProperties());
    }

    public static Config fromConfigFile() {
        Properties props = new Properties();
        /*
        try(var is = Config.class.getResourceAsStream("/config.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properteis", e);
        }
         */

        props.putAll(Map.of(
                "wikipediaUrl", "https://en.wikipedia.org/w/api.php",
                "musicBrainzUrl", "http://musicbrainz.org",
                "coverArtUrl", "http://coverartarchive.org"
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

