package se.billy.git;

import se.billy.git.client.GitClient;
import se.billy.git.domain.GitRepo;

import java.util.List;
import java.util.stream.Collectors;

public interface GitService {
    List<GitRepo> reposForUsers(List<String> users);

    static GitService gitService(GitClient gitClient){
        return users -> users.stream()
                            .flatMap(user -> gitClient.findReposForUser(user).stream())
                            .collect(Collectors.toList());
    }
}
