package se.billy.infra.context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import se.billy.domain.external.coverart.CoverArtClient;
import se.billy.domain.external.musicbrainz.MusicBrainzClient;
import se.billy.domain.external.wikipedia.WikipediaClient;
import se.billy.domain.service.MusicService;
import se.billy.infra.Config;
import se.billy.infra.io.JsonApiClient;

import static se.billy.domain.external.coverart.CoverArtClient.coverArtClient;
import static se.billy.domain.external.musicbrainz.MusicBrainzClient.musicBrainzClient;
import static se.billy.domain.external.wikipedia.WikipediaClient.wikipediaClient;
import static se.billy.domain.service.MusicService.musicService;
import static org.asynchttpclient.Dsl.*;


public class Bootstrapper {
    public static MusicService bootstrap(Config config, Stubs stubs){
        AsyncHttpClient httpClient = asyncHttpClient(config().setFollowRedirect(true));
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonApiClient jsonApiClient = new JsonApiClient(om, httpClient);

        MusicBrainzClient musicBrainzClient = stubs.musicBrainzClient().orElseGet(()-> musicBrainzClient(jsonApiClient, config.musicBrainzUrl));
        WikipediaClient wikipediaClient = stubs.wikipediaClient().orElseGet(()-> wikipediaClient(jsonApiClient, config.wikipediaUrl));
        CoverArtClient coverArtClient = stubs.coverArtClient().orElseGet(()-> coverArtClient(jsonApiClient, config.coverArtUrl));

        return musicService(wikipediaClient, coverArtClient, musicBrainzClient);
    }
}