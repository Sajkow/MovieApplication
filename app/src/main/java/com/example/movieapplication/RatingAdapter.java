package com.example.movieapplication;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {
    private ArrayList<String> movieRating;
    private Context myContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iconStar;
        MyViewHolder(View view) {
            super(view);
            iconStar = view.findViewById(R.id.rating_star_icon);
            myContext = view.getContext();
        }
    }
    public RatingAdapter(ArrayList<String> movieRating) {
        this.movieRating = movieRating;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_star_list, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String star = movieRating.get(position);
        if (star.equals("star")) {
            holder.iconStar.setImageDrawable(myContext.getResources().getDrawable(R.drawable.icon_star));
        } else {
            holder.iconStar.setImageDrawable(myContext.getResources().getDrawable(R.drawable.icon_star_half));
        }
    }
    @Override
    public int getItemCount() {
        return movieRating.size();
    }

    /*
    public RatingAdapter(MovieDescriptionActivity activity, ArrayList<String> stars) {
        super(activity, 0, stars);
        this.movieDescriptionActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String star = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rating_star_list, parent, false);
        }
        // Lookup view for data population
        ImageView iconStar = (ImageView) convertView.findViewById(R.id.rating_star_icon);
        // Populate the data into the template view using the data object
        if (star.equals("star")) {
            iconStar.setImageDrawable(movieDescriptionActivity.getResources().getDrawable(R.drawable.icon_star));
        } else {
            iconStar.setImageDrawable(movieDescriptionActivity.getResources().getDrawable(R.drawable.icon_star_half));
        }
        // Return the completed view to render on screen
        return convertView;
    }*/
}

