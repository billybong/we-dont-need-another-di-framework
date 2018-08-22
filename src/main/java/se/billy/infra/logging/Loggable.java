package se.billy.infra.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public interface Loggable {

    default void log(String s){
        System.out.println(this.getClass().getName() + " : " + s);
    }

    default void log(Object s){
        try {
            String asString = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).writeValueAsString(s);
            System.out.println(this.getClass().getName() + " : " + asString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    default void logError(String s, Throwable t){
        System.err.println(this.getClass().getName() + " : " + s);
        t.printStackTrace(System.err);
    }
}
