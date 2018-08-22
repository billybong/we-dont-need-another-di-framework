package se.billy.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.billy.domain.ArtistId;
import se.billy.domain.ArtistInfo;
import se.billy.domain.service.MusicService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class MusicServer {
    private ObjectMapper om;
    private MusicService musicService;

    public MusicServer(ObjectMapper om, MusicService musicService) {
        this.om = om;
        this.musicService = musicService;
    }

    public void start(){
        after((request, response) -> response.type("application/json"));
        get("/artist/:id", this::artistById, om::writeValueAsString);
    }

    public void shutdown() { stop(); }

    private ArtistInfo artistById(Request req, Response __) {
        var id = ArtistId.from(req.params("id"));
        return musicService.infoForArtist(id);
    }
}