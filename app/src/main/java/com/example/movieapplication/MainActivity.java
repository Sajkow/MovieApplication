package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.movieapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static final String EXTRA_MOVIE_ID = "com.example.movieapplication.extra.MOVIE_ID";

    private MoviesAdapter listAdapter;
    private Handler mainHandler = new Handler();
    private ProgressDialog progressDialog;

    private ArrayList<Movie> movieList;
    private Integer pageNumber = 1;
    private Integer totalPages = 1;
    private String theme;
    private String language;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        theme = getResources().getString(R.string.movie_theme_popular);
        binding.mainHeader.setText(theme);
        language = getResources().getString(R.string.language_english);

        initializeMovieList();

        binding.buttonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pageNumber > 1) {
                    pageNumber--;
                    fetchMovies();
                }
            }
        });
        binding.buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageNumber < totalPages) {
                    pageNumber++;
                    fetchMovies();
                }
            }
        });
    }

    private void initializeMovieList() {
        movieList = new ArrayList<>();
        listAdapter = new MoviesAdapter(this, movieList);
        binding.movieList.setAdapter(listAdapter);

        fetchMovies();
    }

    private void fetchMovies() {
        url = Queries.buildUrlMovies(this, theme.toLowerCase(), language, pageNumber.toString());
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
                    progressDialog = new ProgressDialog(MainActivity.this);
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
                    movieList.clear();
                    JSONObject jsonObject = new JSONObject(data);
                    totalPages = jsonObject.getInt("total_pages");
                    JSONArray movies = jsonObject.getJSONArray("results");
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject result = movies.getJSONObject(i);

                        URL imageUrl = new URL(MainActivity.this.getResources().getString(R.string.image_address) +
                                result.getString("poster_path"));
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream) imageUrl.getContent());

                        movieList.add(new Movie(
                                result.getString("id"),
                                result.getString("title"),
                                bitmap
                        ));
                    }
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
                    if (pageNumber <= 1) { binding.buttonPreviousPage.setEnabled(false); }
                    else { binding.buttonPreviousPage.setEnabled(true); }
                    if (pageNumber >= totalPages) { binding.buttonNextPage.setEnabled(false); }
                    else { binding.buttonNextPage.setEnabled(true); }
                    binding.textviewCurrentPage.setText(pageNumber.toString());
                    listAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void launchMovieDescription(String movieId) {
        Intent intent = new Intent(this, MovieDescriptionActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }
}