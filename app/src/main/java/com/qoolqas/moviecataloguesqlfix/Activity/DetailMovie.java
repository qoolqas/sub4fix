package com.qoolqas.moviecataloguesqlfix.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;
import com.qoolqas.moviecataloguesqlfix.Data.Movie;
import com.qoolqas.moviecataloguesqlfix.Database.MovieHelper;
import com.qoolqas.moviecataloguesqlfix.R;

public class DetailMovie extends AppCompatActivity {
    TextView title, synopsis, rating, release;
    ImageView imageView;
    ProgressBar progressBar;
    Movie movie;
    MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);

        progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);

        title = findViewById(R.id.txt_name1);
        synopsis = findViewById(R.id.txt_synopsis1);
        rating = findViewById(R.id.txt_vote1);
        release = findViewById(R.id.txt_release1);
        imageView = findViewById(R.id.img_detail1);
        progressBar = findViewById(R.id.pb1);

        movie = getIntent().getParcelableExtra("movies");
        {
            progressBar.setVisibility(View.VISIBLE);
            String imageView1 = movie.getPosterPath();
            String title1 = movie.getTitle();
            String synopsis1 = movie.getOverview();
            String rating1 = Double.toString(movie.getVoteAverage());
            String release1 = movie.getReleaseDate();

            String image_url = "https://image.tmdb.org/t/p/w185" + imageView1;

            Glide.with(this).load(image_url).placeholder(R.drawable.load).into(imageView);
            title.setText(title1);
            synopsis.setText(synopsis1);
            rating.setText(rating1);
            release.setText(release1);
            final MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favorite_button);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite) {
                                SharedPreferences.Editor editor = getSharedPreferences("com.qoolqas.moviecataloguesql.DetailMovie", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Added", true);
                                editor.apply();
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                int movie_id = getIntent().getExtras().getInt("id");
                                movieHelper = new MovieHelper(DetailMovie.this);
                                movieHelper.open();
                                movieHelper.deleteMovie(movie_id);
                                movieHelper.close();
                                SharedPreferences.Editor editor = getSharedPreferences("com.qoolqas.moviecataloguesql.DetailMovie", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Removed", true);
                                editor.apply();

                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

        }
        progressBar.setVisibility(View.GONE);
    }

    public void saveFavorite() {
        movieHelper = new MovieHelper(this);
        int movie_id = movie.getId();
        String poster = movie.getPosterPath();
        String title = movie.getTitle();
        String rating = Double.toString(movie.getVoteAverage());
        String synopsis = movie.getOverview();
        String release = movie.getReleaseDate();

        movie.setId(movie_id);
        movie.setPosterPath(poster);
        movie.setTitle(title);
        movie.setVoteAverage(Double.parseDouble(rating));
        movie.setOverview(synopsis);
        movie.setReleaseDate(release);

        movieHelper.open();
        movieHelper.insertMovie(movie);
        movieHelper.close();

    }

}
