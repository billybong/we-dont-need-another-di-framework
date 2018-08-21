package se.billy.context;

import se.billy.App;
import se.billy.Config;

public class Bootstrapper {
    public static App bootstrap(Config config, Factories factories){
        var httpClient = factories.httpClient(config.gitHubBaseUrl);
        var gitClient = factories.gitClient(httpClient);
        var gitService = factories.gitService(gitClient);

        return new App(gitService);
    }
}
