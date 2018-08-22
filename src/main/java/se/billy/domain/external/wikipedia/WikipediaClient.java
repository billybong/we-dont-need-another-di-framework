package se.billy.domain.external.wikipedia;

import se.billy.domain.external.wikipedia.domain.WikipediaArticle;
import se.billy.infra.io.JsonApiClient;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface WikipediaClient {
    CompletableFuture<Optional<WikipediaArticle>> fetchWikipage(String title);

    static WikipediaClient wikipediaClient(JsonApiClient apiClient, String baseUrl) {
        return new WikipediaClient() {
            @Override
            public CompletableFuture<Optional<WikipediaArticle>> fetchWikipage(String title) {
                var uri = String.format("%s?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles=%s", baseUrl, title);
                return apiClient.fetch(uri, WikipediaArticle.class);
            }
        };
    }
}
