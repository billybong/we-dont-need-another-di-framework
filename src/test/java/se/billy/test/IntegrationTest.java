package se.billy.test;

import org.junit.Test;
import se.billy.context.Factories;
import se.billy.git.client.GitClient;
import se.billy.git.domain.GitRepo;
import se.billy.io.HttpClient;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static se.billy.context.Bootstrapper.bootstrap;
import static se.billy.git.domain.GitRepo.gitRepo;

public class IntegrationTest {

    @Test
    public void mockGitClient() {
        var context = bootstrap(config, new Factories(){
            @Override
            public GitClient gitClient(HttpClient httpClient) {
                return (whichEverUserName -> List.of(gitRepo("Node.JS")));
            }
        });

        List<GitRepo> repos = context.gitService.reposForUsers(List.of("Billy"));
        String name = repos.get(0).name();
        assertEquals("Node.JS", name);
    }
}
