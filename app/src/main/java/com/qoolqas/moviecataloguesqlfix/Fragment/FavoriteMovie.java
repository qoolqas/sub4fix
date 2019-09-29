package com.qoolqas.moviecataloguesqlfix.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qoolqas.moviecataloguesqlfix.Adapter.MovieAdapter;
import com.qoolqas.moviecataloguesqlfix.Data.Movie;
import com.qoolqas.moviecataloguesqlfix.Database.MovieHelper;
import com.qoolqas.moviecataloguesqlfix.R;

import java.util.ArrayList;

public class FavoriteMovie extends Fragment {

    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieHelper movieHelper;
    private MovieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieHelper = MovieHelper.getInstance(view.getContext());
        movieHelper.open();

        RecyclerView rvFavoriteFragmentMovies = view.findViewById(R.id.recyclerview);
        rvFavoriteFragmentMovies.setHasFixedSize(true);
        rvFavoriteFragmentMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new MovieAdapter(view.getContext());
        adapter.setMovies(movies);
        rvFavoriteFragmentMovies.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        movies = movieHelper.getAllMovies();
        adapter.setData(movies);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
