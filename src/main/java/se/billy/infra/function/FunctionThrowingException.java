package se.billy.infra.function;

public interface FunctionThrowingException<T,R> {
    R apply(T t) throws Exception;
}
