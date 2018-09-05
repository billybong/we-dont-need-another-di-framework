package se.billy.delivery.aws;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.billy.domain.ArtistId;
import se.billy.domain.ArtistInfo;
import se.billy.domain.service.MusicService;
import se.billy.infra.context.Bootstrapper;

import java.util.concurrent.ExecutionException;

import static se.billy.infra.Config.fromConfigFile;
import static se.billy.infra.context.Stubs.noStubs;

public class LambdaEndpoint implements RequestHandler<Object, ArtistInfo> {

    private final static MusicService service = Bootstrapper.bootstrap(fromConfigFile(), noStubs());
    private final static String NIRVANA = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";

    @Override
    public ArtistInfo handleRequest(Object input, Context context) {
        try {
            return service.infoForArtist(ArtistId.from(NIRVANA)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ArtistInfo artistInfo = new LambdaEndpoint().handleRequest(null, null);
        System.out.println(new ObjectMapper().writeValueAsString(artistInfo));
    }
}