package se.billy.domain.external.musicbrainz.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.billy.domain.ArtistId;

import java.net.URL;
import java.util.List;

public class MusicBrainzInfo {
    public String id;
    public String name;
    public List<Relations> relations;
    @JsonProperty("release-groups")
    public List<ReleaseGroup> releaseGroups;


    public static class Relations{
        public String type;
        public RelationUrl url;
    }

    public static class RelationUrl{
        public URL resource;
    }

    public static class ReleaseGroup{
        String id;
        public String title;
        @JsonProperty("primary-type")
        public String primaryType;

        public ArtistId getId(){ return ArtistId.from(id); }
    }
}