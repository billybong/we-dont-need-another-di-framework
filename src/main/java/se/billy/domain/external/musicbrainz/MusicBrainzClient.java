package se.billy.domain.external.musicbrainz;

import se.billy.domain.ArtistId;
import se.billy.domain.external.musicbrainz.domain.MusicBrainzInfo;
import se.billy.infra.io.JsonApiClient;

import java.util.concurrent.CompletableFuture;

public interface MusicBrainzClient {

    CompletableFuture<MusicBrainzInfo> fetchInfoById(ArtistId id);

    static MusicBrainzClient musicBrainzClient(JsonApiClient client, String musicBrainzUrl) {
        return id -> {
            String uriString = String.format("%s/ws/2/artist/%s?&fmt=json&inc=url-rels+release-groups", musicBrainzUrl, id.value());
            return client.fetch(uriString, MusicBrainzInfo.class)
                    .thenApply(it -> it.orElseThrow(()-> new RuntimeException("Could find any MusicBrainzInfo")));
        };
    }
}