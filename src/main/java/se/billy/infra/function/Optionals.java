package se.billy.infra.function;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Optionals {
    public static <T> List<T> removeEmpties(List<Optional<T>> optionals) {
        return optionals.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}