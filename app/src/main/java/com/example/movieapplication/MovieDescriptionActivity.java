package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.movieapplication.databinding.ActivityMovieDescriptionBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MovieDescriptionActivity extends AppCompatActivity {
    private ActivityMovieDescriptionBinding binding;

    private ProgressDialog progressDialog;
    private Handler mainHandler = new Handler();

    private Movie movie;
    private String language;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String movieId = intent.getStringExtra(MainActivity.EXTRA_MOVIE_ID);

        language = getResources().getString(R.string.language_english);
        url = Queries.buildUrlMovieDescription(this, movieId, language);
        movie = new Movie();

        new fetchData().start();
    }

    //My Inner CLASS
    private class fetchData extends Thread {
        String data = "";

        @Override
        public void run() {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MovieDescriptionActivity.this);
                    progressDialog.setMessage("Fetching movies, grab some Popcorn!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    data = data + line;
                }
                if (!data.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(data);

                    JSONArray genreJSON = jsonObject.getJSONArray("genres");
                    ArrayList<String> genres = new ArrayList<>();
                    for (int g = 0; g < genreJSON.length(); g++) {
                        JSONObject tempObject = genreJSON.getJSONObject(g);
                        genres.add(tempObject.getString("name"));
                    }

                    URL imageUrl = new URL(MovieDescriptionActivity.this.getResources().getString(R.string.image_address) +
                            jsonObject.getString("poster_path"));
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) imageUrl.getContent());

                    movie.setTitle(jsonObject.getString("title"));
                    movie.setGenres(genres);
                    movie.setOverview(jsonObject.getString("overview"));
                    movie.setImage(bitmap);
                    movie.setTagLine(jsonObject.getString("tagline"));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    binding.movieDescriptionHeader.setText(movie.getTitle());
                    binding.movieDescriptionPoster.setImageBitmap(movie.getImage());
                    binding.movieDescriptionGenres.setText(movie.getGenres());
                    binding.movieDescriptionPoster.setContentDescription("Poster for " + movie.getTitle());
                    binding.movieDescriptionTagline.setText(movie.getTagLine());
                    binding.movieDescriptionOverview.setText(movie.getOverview());
                }
            });
        }
    }
}