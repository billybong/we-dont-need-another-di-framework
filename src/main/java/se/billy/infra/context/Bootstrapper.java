package se.billy.infra.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.http.HttpClient;
import se.billy.infra.Config;
import se.billy.infra.MusicServer;
import se.billy.infra.io.JsonApiClient;

import static se.billy.domain.external.coverart.CoverArtClient.coverArtClient;
import static se.billy.domain.external.musicbrainz.MusicBrainzClient.musicBrainzClient;
import static se.billy.domain.external.wikipedia.WikipediaClient.wikipediaClient;
import static se.billy.domain.service.MusicService.musicService;

public class Bootstrapper {
    public static MusicServer bootstrap(Config config, Stubs stubs){
        var httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        var om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var jsonApiClient = new JsonApiClient(om, httpClient);

        var musicBrainzClient = stubs.musicBrainzClient().orElseGet(()-> musicBrainzClient(jsonApiClient, config.musicBrainzUrl));
        var wikipediaClient = stubs.wikipediaClient().orElseGet(()-> wikipediaClient(jsonApiClient, config.wikipediaUrl));
        var coverArtClient = stubs.coverArtClient().orElseGet(()-> coverArtClient(jsonApiClient, config.coverArtUrl));

        var musicService = musicService(wikipediaClient, coverArtClient, musicBrainzClient);

        return new MusicServer(om, musicService);
    }
}