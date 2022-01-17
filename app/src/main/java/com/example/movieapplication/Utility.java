package com.example.movieapplication;

import android.content.Context;

import java.util.ArrayList;

public class Utility {
    private static final ArrayList<String> loadingStrings;

    static {
        loadingStrings = new ArrayList<>();
        loadingStrings.add("Fetching movies, grab some Popcorn!");
        loadingStrings.add("I'll be baack.");
        loadingStrings.add("01001000 01101001 00100001");
        loadingStrings.add("They may take our lives, but they'll never take our freedom!");
        loadingStrings.add("You're just a stomach flu away from the ideal weight!");
        loadingStrings.add("Magic mirror on the wall, which are the most popular of them all?");
        loadingStrings.add("Elementary, my dear Watson!");
    }
    public static int dpToPixels(int dp, Context myContext) {
        double density = myContext.getResources().getDisplayMetrics().density;
        return (int) Math.round((double) dp * density);
    }

    public static String getQuote() {
        return loadingStrings.get((int) (Math.random() * loadingStrings.size()));
    }
}
