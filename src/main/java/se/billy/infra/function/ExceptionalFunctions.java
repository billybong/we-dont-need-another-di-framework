package se.billy.infra.function;

import java.util.function.Function;

public class ExceptionalFunctions {
    public static <T, R, RE extends RuntimeException> Function<T, R> rethrowsAsRuntime(FunctionThrowingException<T, R> ex, Function<Exception, RE> runtimeExceptionsMapper) {
        return (T t) -> {
            try {
                return ex.apply(t);
            } catch (Exception e) {
                throw runtimeExceptionsMapper.apply(e);
            }
        };
    }
}
