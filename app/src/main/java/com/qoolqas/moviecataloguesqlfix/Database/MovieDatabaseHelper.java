package com.qoolqas.moviecataloguesqlfix.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.qoolqas.moviecataloguesqlfix.Database.MovieContract.MovieColumns.MOVIE_TABLE_NAME;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String MOVIE_DATABASE_NAME = "dbmovie";

    private static final int MOVIE_DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
                    " (%s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)",
            MOVIE_TABLE_NAME,
//            MovieColumns._ID,
            MovieContract.MovieColumns.ID,
            MovieContract.MovieColumns.TITLE,
            MovieContract.MovieColumns.OVERVIEW,
            MovieContract.MovieColumns.RELEASE_DATE,
            MovieContract.MovieColumns.VOTE_AVERAGE,
            MovieContract.MovieColumns.POSTER_PATH_STRING
            );

    MovieDatabaseHelper(Context context) {
        super(context, MOVIE_DATABASE_NAME, null, MOVIE_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
