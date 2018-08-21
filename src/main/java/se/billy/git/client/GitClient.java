package se.billy.git.client;

import se.billy.io.HttpClient;
import se.billy.git.domain.GitRepo;

import java.util.List;

import static se.billy.git.domain.GitRepo.gitRepo;

public interface GitClient {
    List<GitRepo> findReposForUser(String userName);

    static GitClient gitClient(HttpClient httpClient){
        return userName -> List.of(
                gitRepo("Wildfly"),
                gitRepo("Camel")
        );
    }
}
