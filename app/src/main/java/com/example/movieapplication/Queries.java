package com.example.movieapplication;

import android.content.Context;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Queries {
    private static final String LOG_KEY = Queries.class.getSimpleName();

    public static URL buildUrlMovies(Context context, String theme, String language, String pageNumber) {
        String serverAddress = context.getResources().getString(R.string.server_address);
        String apiKey = "?api_key=" + context.getResources().getString(R.string.api_key);
        language = "&language=" + language;
        pageNumber = "&page=" + pageNumber;
        try {
            URL url = new URL(serverAddress + theme + apiKey + language + pageNumber);
            Log.d(LOG_KEY, "Resulting URL: " + url.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL buildUrlMovieDescription(Context context, String movieId, String language) {
        String serverAddress = context.getResources().getString(R.string.server_address);
        String apiKey = "?api_key=" + context.getResources().getString(R.string.api_key);
        language = "&language=" + language;

        try {
            URL url = new URL(serverAddress + movieId + apiKey + language);
            Log.d(LOG_KEY, "Resulting URL: " + url.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
