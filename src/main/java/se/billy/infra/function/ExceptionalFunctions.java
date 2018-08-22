package se.billy.infra.function;

import java.util.function.Function;

public class ExceptionalFunctions {
    public static <T, R> Function<T, R> propagatingException(FunctionThrowingException<T, R> ex) {
        return (T t) -> {
            try {
                return ex.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
