module musicserver {
    requires jdk.incubator.httpclient;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires jackson.annotations;
    requires com.fasterxml.jackson.core;
    requires javalin;

    exports se.billy.domain.external.musicbrainz.domain;
    opens se.billy.domain.external.musicbrainz.domain;

    exports se.billy.domain.external.wikipedia.domain;
    opens se.billy.domain.external.wikipedia.domain;

    exports se.billy.domain.external.coverart.domain;
    opens se.billy.domain.external.coverart.domain;

    exports se.billy.domain;
    opens se.billy.domain;
}