package se.billy.domain.external.coverart;

import se.billy.domain.ArtistId;
import se.billy.domain.external.coverart.domain.CoverArt;
import se.billy.infra.io.JsonApiClient;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CoverArtClient {

    CompletableFuture<Optional<CoverArt>> fetchAlbumArtById(ArtistId id);

    static CoverArtClient coverArtClient(JsonApiClient httpClient, String coverArtUrl){
        return id -> {
            String uri = String.format("%s/release-group/%s", coverArtUrl, id.value());
            return httpClient.fetch(uri, CoverArt.class);
        };
    }
}