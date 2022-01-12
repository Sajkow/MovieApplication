package com.example.movieapplication;

import android.graphics.Bitmap;
import android.util.Log;
import java.util.ArrayList;

public class Movie {
    private String id;
    private String title;
    private ArrayList<String> genres;
    private String tagLine;
    private String overview;
    private Bitmap image;

    public Movie() { }

    public Movie(
            String id,
            String title,
            ArrayList<String> genres,
            String overview,
            Bitmap image
    ) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.overview = overview;
        this.image = image;
        Log.d("Created movie: ", "id" + id + " title: " + title + " genres: " + genres);
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setGenres(ArrayList<String> genres) { this.genres = genres; }
    public String getGenres() {
        String string = "";
        for (int i = 0; i < genres.size(); i++) {
            string += genres.get(i);
            if (i < genres.size() - 1) { string += ", "; }
            else { string += "."; }
        }
        return string;
    }

    public void setOverview(String overview) { this.overview = overview; }
    public String getOverview() { return overview; }

    public void setImage(Bitmap image) { this.image = image; }
    public Bitmap getImage() { return image; }

    public void setTagLine(String tagLine) { this.tagLine = tagLine; }
    public String getTagLine() { return tagLine; }
}
