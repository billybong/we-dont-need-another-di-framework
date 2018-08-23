package se.billy.domain.service;

import se.billy.domain.ArtistId;
import se.billy.domain.ArtistInfo;
import se.billy.domain.external.coverart.CoverArtClient;
import se.billy.domain.external.coverart.domain.CoverArt;
import se.billy.domain.external.musicbrainz.MusicBrainzClient;
import se.billy.domain.external.musicbrainz.domain.MusicBrainzInfo;
import se.billy.domain.external.wikipedia.WikipediaClient;
import se.billy.domain.external.wikipedia.domain.WikipediaArticle;
import se.billy.infra.function.Futures;
import se.billy.infra.logging.Loggable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public interface MusicService extends Loggable {
    CompletableFuture<ArtistInfo> infoForArtist(ArtistId id);

    static MusicService musicService(WikipediaClient wikipediaClient, CoverArtClient coverArtClient, MusicBrainzClient musicBrainzClient){
        return new MusicService() {
            @Override
            public CompletableFuture<ArtistInfo> infoForArtist(ArtistId id) {
                return infoForArtistThrows(id);
            }

            private CompletableFuture<ArtistInfo> infoForArtistThrows(ArtistId id) {
                return musicBrainzClient.fetchInfoById(id)
                        .thenCompose(musicBrainzInfo -> {
                            var wikipageFuture = fetchWikiarticle(musicBrainzInfo);

                            List<CompletableFuture<Optional<CoverArt>>> albumsResponsesFutures = musicBrainzInfo.releaseGroups.stream()
                                    .filter(it -> "Album".equals(it.primaryType))
                                    .map(it -> coverArtClient.fetchAlbumArtById(it.getId()))
                                    .collect(Collectors.toList());

                            var albumsFuture = Futures.sequence(albumsResponsesFutures)
                                    .thenApply(it -> it.stream()
                                                        .flatMap(Optional::stream)
                                                        .collect(Collectors.toList())
                                    );

                            return albumsFuture.thenCombine(
                                    wikipageFuture,
                                    (albums, wikipage) -> ArtistInfo.from(musicBrainzInfo, wikipage, albums)
                            );
                        });
            }

            private CompletableFuture<Optional<WikipediaArticle>> fetchWikiarticle(MusicBrainzInfo musicBrainzInfo) {
                var mayWikiTitle = musicBrainzInfo.relations.stream()
                        .filter(it -> "wikipedia".equals(it.type))
                        .map(it -> it.url.resource.getPath().split("/"))
                        .map(array -> array[array.length-1])
                        .findFirst();

                return mayWikiTitle.map(wikipediaClient::fetchWikipage)
                        .orElse(CompletableFuture.completedFuture(Optional.empty()));
            }
        };
    }
}