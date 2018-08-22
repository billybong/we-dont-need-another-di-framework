package se.billy.domain;

public class ArtistId {
    private String id;
    private ArtistId(String id) {
        this.id = id;
    }

    public String value(){
        return id;
    }

    public static ArtistId from(String id){
        return new ArtistId(id);
    }
}
