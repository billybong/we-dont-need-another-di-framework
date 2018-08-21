package se.billy;

import se.billy.context.Bootstrapper;
import se.billy.git.domain.GitRepo;

import java.util.List;

import static se.billy.context.Factories.factories;

public class Main {

    public static void main(String[] args) {
        var config = Config.fromSystemProperties();
        var context = Bootstrapper.bootstrap(config, factories());

        context.gitService.reposForUsers(List.of("Billy", "Maria"))
                .stream()
                .map(GitRepo::name)
                .forEach(System.out::println);
    }
}