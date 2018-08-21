package se.billy.context;

import se.billy.git.GitService;
import se.billy.git.client.GitClient;
import se.billy.io.HttpClient;

import java.net.URL;

public interface Factories {
    default HttpClient httpClient(URL baseUrl) { return new HttpClient(baseUrl); }
    default GitClient gitClient(HttpClient httpClient){ return GitClient.gitClient(httpClient);}
    default GitService gitService(GitClient gitClient){ return GitService.gitService(gitClient);}

    static Factories factories(){return new Factories(){};}
}
