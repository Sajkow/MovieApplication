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
            holder.iconStar.getLayoutParams().width = Utility.dpToPixels(10, myContext);
            holder.iconStar.setImageDrawable(myContext.getResources().getDrawable(R.drawable.icon_star_half));
        }
    }
    @Override
    public int getItemCount() {
        return movieRating.size();
    }
}

