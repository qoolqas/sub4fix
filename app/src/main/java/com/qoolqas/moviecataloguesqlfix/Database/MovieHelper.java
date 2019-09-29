package com.qoolqas.moviecataloguesqlfix.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.qoolqas.moviecataloguesqlfix.Data.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.ID;
import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.MOVIE_TABLE_NAME;
import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.OVERVIEW;
import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.POSTER_PATH_STRING;
import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.RELEASE_DATE;
import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.TITLE;
import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.VOTE_AVERAGE;

public class MovieHelper {
    private static final String DATABASE_TABLE = MOVIE_TABLE_NAME;
    private static MovieDatabaseHelper movieDatabaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        movieDatabaseHelper = new MovieDatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = movieDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        movieDatabaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH_STRING)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(RELEASE_DATE, movie.getReleaseDate());
        args.put(VOTE_AVERAGE, movie.getVoteAverage());
        args.put(POSTER_PATH_STRING, movie.getPosterPath());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return database.delete(MOVIE_TABLE_NAME, ID + " = '" + id + "'", null);
    }
}
