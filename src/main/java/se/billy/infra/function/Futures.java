package se.billy.infra.function;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Futures {

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures){
        CompletableFuture[] cfs = futures.toArray(new CompletableFuture[futures.size()]);
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(cfs);

        return allDoneFuture.thenApply(ignored ->
                futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.toList())
        );
    }
}
