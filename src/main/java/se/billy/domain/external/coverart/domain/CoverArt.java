package se.billy.domain.external.coverart.domain;

import java.net.URL;
import java.util.List;

public class CoverArt {
    public List<Image> images;

    public static class Image{
        public URL image;
    }
}