package se.billy.domain.external.wikipedia.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.SortedMap;

public class WikipediaArticle {
    public Query query;

    public static class Query{
        public SortedMap<Integer, WikiPage> pages;
    }

    public static class WikiPage{
        public final String text;

        @JsonCreator
        public WikiPage(@JsonProperty("extract") String extract) {
            this.text = extract.replace("\"", "'");
        }
    }
}