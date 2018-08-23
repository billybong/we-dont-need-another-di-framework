package se.billy;

import se.billy.infra.Config;
import se.billy.infra.context.Bootstrapper;

import static se.billy.infra.context.Stubs.noStubs;

public class Main {

    public static void main(String[] args) {
        var config = Config.fromConfigFile();
        var app = Bootstrapper.bootstrap(config, noStubs());

        app.start(config.port);
    }
}