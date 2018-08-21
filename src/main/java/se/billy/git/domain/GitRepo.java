package se.billy.git.domain;

public interface GitRepo {
    String name();
    static GitRepo gitRepo(String name){
        return () -> name;
    }
}
