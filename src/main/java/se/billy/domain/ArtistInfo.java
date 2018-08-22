package se.billy.domain;

import se.billy.domain.external.coverart.domain.CoverArt;
import se.billy.domain.external.musicbrainz.domain.MusicBrainzInfo;
import se.billy.domain.external.wikipedia.domain.WikipediaArticle;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArtistInfo {
    public String id;
    public List<Album> albums;
    public String info;

    private ArtistInfo() { }

    public static ArtistInfo from(MusicBrainzInfo musicBrainzInfo, Optional<WikipediaArticle> wikiArticle, List<CoverArt> albums) {
        var artistInfo = new ArtistInfo();

        artistInfo.id = musicBrainzInfo.id;
        artistInfo.info = wikiArticle.map(it -> it.query.pages.values().iterator().next().text).orElse(null);
        artistInfo.albums = albums.stream()
                .map(it -> new Album("lorum ipsum", it.images.get(0).image))
                .collect(Collectors.toList());

        return artistInfo;
    }

    public static class Album {
        public String name;
        public URL imageUrl;

        public Album(String name, URL imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }
    }
}
