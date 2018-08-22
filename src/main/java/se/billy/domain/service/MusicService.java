package se.billy.domain.service;

import se.billy.domain.ArtistId;
import se.billy.domain.ArtistInfo;
import se.billy.domain.external.coverart.CoverArtClient;
import se.billy.domain.external.musicbrainz.MusicBrainzClient;
import se.billy.domain.external.musicbrainz.domain.MusicBrainzInfo;
import se.billy.domain.external.wikipedia.WikipediaClient;
import se.billy.domain.external.wikipedia.domain.WikipediaArticle;
import se.billy.infra.logging.Loggable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public interface MusicService extends Loggable {
    ArtistInfo infoForArtist(ArtistId id);

    static MusicService musicService(WikipediaClient wikipediaClient, CoverArtClient coverArtClient, MusicBrainzClient musicBrainzClient){
        return new MusicService() {
            @Override
            public ArtistInfo infoForArtist(ArtistId id) {
                return infoForArtistThrows(id);
            }

            private ArtistInfo infoForArtistThrows(ArtistId id) {
                var musicBrainzInfo = musicBrainzClient.fetchInfoById(id).join();
                log(musicBrainzInfo);

                var wikiArticleFuture = fetchWikiarticle(musicBrainzInfo);

                var albumsResponsesFutures = musicBrainzInfo.releaseGroups.stream()
                        .filter(it -> "Album".equals(it.primaryType))
                        .map(it -> coverArtClient.fetchAlbumArtById(it.getId()))
                        .collect(Collectors.toList());

                var albums = albumsResponsesFutures.stream()
                        .flatMap(it -> it.join().stream())
                        .collect(Collectors.toList());

                var wikiArticle = wikiArticleFuture.join();

                return ArtistInfo.from(musicBrainzInfo, wikiArticle, albums);
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