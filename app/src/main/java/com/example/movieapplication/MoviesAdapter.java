package com.example.movieapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MoviesAdapter extends ArrayAdapter<Movie> {
    private MainActivity mainActivity;

    public MoviesAdapter(MainActivity activity, ArrayList<Movie> movies) {
        super(activity, 0, movies);
        this.mainActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.title_list, parent, false);
        }
        // Lookup view for data population
        ImageView moviePoster = (ImageView) convertView.findViewById(R.id.movie_poster);
        TextView movieTitle = (TextView) convertView.findViewById(R.id.movie_title);
        // Populate the data into the template view using the data object
        moviePoster.setImageBitmap(movie.getImage());
        moviePoster.setContentDescription("Poster for " + movie.getTitle());
        movieTitle.setText(movie.getTitle());
        // Return the completed view to render on screen

        //Add listeners to every row.
        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Movie movie = getItem(position);
                mainActivity.launchMovieDescription(movie.getId());
            }
        });
        return convertView;
    }
}

