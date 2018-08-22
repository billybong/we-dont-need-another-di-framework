package se.billy.infra.context;

import se.billy.domain.external.coverart.CoverArtClient;
import se.billy.domain.external.musicbrainz.MusicBrainzClient;
import se.billy.domain.external.wikipedia.WikipediaClient;

import java.util.Optional;

import static java.util.Optional.empty;

public interface Stubs {
    default Optional<WikipediaClient> wikipediaClient(){ return empty();}
    default Optional<MusicBrainzClient> musicBrainzClient(){return empty();}
    default Optional<CoverArtClient> coverArtClient(){return empty();}

    static Stubs noStubs(){return new Stubs(){};}

}
