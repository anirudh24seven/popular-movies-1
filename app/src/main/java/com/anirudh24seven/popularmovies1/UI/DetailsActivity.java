package com.anirudh24seven.popularmovies1.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.anirudh24seven.popularmovies1.Models.Movie;
import com.anirudh24seven.popularmovies1.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private Movie currentMovie;
    private TextView movieNameTextView;
    private TextView releaseYearTextView;
    private TextView voteTextView;
    private ImageView movieImageView;
    private TextView plotTextView;

    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/" + "w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras()!= null){
            currentMovie = (Movie) getIntent().getSerializableExtra("movie");
        }

        movieNameTextView = (TextView) findViewById(R.id.movie_name);
        movieImageView = (ImageView) findViewById(R.id.movie_image);
        releaseYearTextView = (TextView) findViewById(R.id.release_year_text);
        voteTextView = (TextView) findViewById(R.id.vote_text);
        plotTextView = (TextView) findViewById(R.id.plot_synopsis);

        if(currentMovie!=null) {
            movieNameTextView.setText(currentMovie.title);
            releaseYearTextView.setText(getString(R.string.release_header_text) + currentMovie.releaseDate);
            voteTextView.setText(getString(R.string.ratings_header_text) + currentMovie.voteAverage + "/10");

            String fullImageUrl = IMAGE_BASE_URL + currentMovie.imageUrl;
            Picasso.with(DetailsActivity.this).load(fullImageUrl).into(movieImageView);

            plotTextView.setText(currentMovie.overview);
        }
    }
}
