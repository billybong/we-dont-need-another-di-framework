package se.billy;

import se.billy.git.GitService;
import se.billy.git.domain.GitRepo;

import java.util.List;

public class App {
    private GitService gitService;

    public App(GitService gitService) {
        this.gitService = gitService;
    }

    public List<GitRepo> findReposForUsers(){
        return gitService.reposForUsers(List.of("Billy", "Maria"));
    }
}
