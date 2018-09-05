package se.billy.infra.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;

public interface Loggable {

    default void log(String s){
        LogManager.getLogger(this.getClass().getName()).info(s);
    }

    default void log(Object s){
        try {
            String asString = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).writeValueAsString(s);
            LogManager.getLogger(this.getClass().getName()).info(asString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    default void logError(String s, Throwable t){
        LogManager.getLogger(this.getClass().getName()).error(s);
    }
}
