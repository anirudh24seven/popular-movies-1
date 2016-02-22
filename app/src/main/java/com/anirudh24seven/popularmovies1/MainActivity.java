package com.anirudh24seven.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anirudh24seven.popularmovies1.Models.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private String moviesApiUrl =  BASE_URL + "discover/movie?sort_by=popularity.desc&api_key=" + API_KEY;

    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/" + "w185/";

    List<Movie> movieResultList;
    private GridView gridView;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieResultList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.movies_grid);

        fetchMovies();
    }

    private void fetchMovies() {
        JsonObjectRequest request = new JsonObjectRequest(moviesApiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject resultItem = results.getJSONObject(i);

                        Movie movie = new Movie();

                        movie.title = resultItem.getString("title");
                        movie.imageUrl = resultItem.getString("poster_path");
                        movie.overview = resultItem.getString("overview");
                        movie.releaseDate = resultItem.getString("release_date");
                        movie.voteAverage = String.valueOf(resultItem.getDouble("vote_average"));

                        Log.d(TAG, "onResponse: Movie title: " + movie.title);

                        movieResultList.add(movie);
                    }

                    moviesAdapter = new MoviesAdapter(MainActivity.this, movieResultList);
                    gridView.setAdapter(moviesAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                            intent.putExtra("movie", movieResultList.get(position));
                            startActivity(intent);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
    }

    private class MoviesAdapter extends BaseAdapter {

        private Context context;
        private List<Movie> moviesList;

        public MoviesAdapter(Context context, List<Movie> movieResultList) {
            this.context = context;
            this.moviesList = movieResultList;
        }

        @Override
        public int getCount() {
            return moviesList.size();
        }

        @Override
        public Object getItem(int position) {
            return moviesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;
            ImageView movieImageView;
            TextView movieTitleView;

            if (convertView == null) {
                gridView = inflater.inflate(R.layout.movie_row, null);

            }
            else {
                gridView = convertView;
            }

            movieImageView = (ImageView) gridView.findViewById(R.id.movie_image);
            movieTitleView = (TextView) gridView.findViewById(R.id.movie_title);

            Movie movie = moviesList.get(position);
            movieTitleView.setText(movie.title);
            String fullImageUrl = IMAGE_BASE_URL + movie.imageUrl;
            Picasso.with(context).load(fullImageUrl).into(movieImageView);

            return gridView;
        }
    }
}
