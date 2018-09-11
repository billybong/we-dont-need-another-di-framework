package se.billy.delivery.azure;


import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import se.billy.domain.ArtistId;
import se.billy.domain.ArtistInfo;
import se.billy.domain.service.MusicService;
import se.billy.infra.context.Bootstrapper;

import static se.billy.infra.Config.fromConfigFile;
import static se.billy.infra.context.Stubs.noStubs;

public class LambdaEndpoint {

    private final static MusicService service = Bootstrapper.bootstrap(fromConfigFile(), noStubs());
    private final static String NIRVANA = "5b11f4ce-a62d-471e-81fc-a69a8278c7da";

    @FunctionName("musicinfo")
    public ArtistInfo handleRequest(@HttpTrigger(name = "artistinfo", authLevel = AuthorizationLevel.ANONYMOUS, route = "artist")HttpRequestMessage<Void> requestMessage) {
        return service.infoForArtist(ArtistId.from(NIRVANA)).join();
    }
}