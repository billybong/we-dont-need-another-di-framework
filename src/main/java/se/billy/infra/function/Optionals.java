package se.billy.infra.function;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Optionals {
    public static <T> List<T> removeEmpties(List<Optional<T>> optionals) {
        return optionals.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}