package com.example.movieapplication;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class Movie {
    private String id;
    private String title;
    private ArrayList<String> genres;
    private String tagLine;
    private String overview;
    private Bitmap image;
    private String videoLink;
    private Double rating;

    public Movie(String title, Bitmap image) {
        this.title = title;
        this.image = image;
    }

    public Movie(String id, String title, Bitmap image) {
        this(title, image);
        this.id = id;
    }

    public Movie(
            String title,
            ArrayList<String> genres,
            String overview,
            String tagLine,
            Bitmap image,
            Double rating
    ) {
        this(title, image);
        this.genres = genres;
        this.overview = overview;
        this.tagLine = tagLine;
        this.rating = rating;
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

    public void setRating(Double rating) { this.rating = rating; }
    //Return a rating at 0.50 increments.
    public ArrayList<String> getRating() {
        ArrayList<String> list = new ArrayList<>();
        Double rest = rating % 1;
        Double result;
        boolean halfStar = false;

        if((rest + 0.50) - 1 >= 0.25 && (rest + 0.50) - 1 <= 0.50) {
            result = Math.ceil(rating);
        } else if ((rest - 0.50) >= -0.50 && (rest - 0.50) <= -0.25) {
            result = Math.floor(rating);
        } else {
            result = (double) rating.intValue() + 0.50;
            halfStar = true;
        }

        for(int i = 0; i < result.intValue(); i++) { list.add("star"); }
        if (halfStar) { list.add("starHalf"); }
        return list;
    }
}
