package se.billy.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Context;
import io.javalin.Javalin;
import se.billy.domain.ArtistId;
import se.billy.domain.service.MusicService;

import java.util.Map;

public class MusicServer {
    private final Javalin app;
    private ObjectMapper om;
    private MusicService musicService;

    public MusicServer(ObjectMapper om, MusicService musicService) {
        this.om = om;
        this.musicService = musicService;
        this.app = Javalin.create();
    }

    public void start(int port){
        app.get("/api/artist/:id", this::artistById);
        app.start(port);
    }

    private void artistById(Context ctx) {
        var artistId = ArtistId.from(ctx.pathParam("id"));
        var artistInfoFuture = musicService.infoForArtist(artistId);

        var jsonFuture = artistInfoFuture
                .thenApply(this::serializeJson)
                .exceptionally(e -> {
                    ctx.status(503);
                    return serializeErrorMsg(e);
                });
        ctx.result(jsonFuture).contentType("application/json");
    }

    public void shutdown() { app.stop(); }

    private String serializeErrorMsg(Throwable e) {
        return serializeJson(Map.of(
                "error", e.getMessage()
        ));
    }

    private String serializeJson(Object o){
        try {
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serializeJson object " + o.getClass().getName(), e);
        }
    }
}