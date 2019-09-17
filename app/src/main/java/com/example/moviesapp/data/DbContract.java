package com.example.moviesapp.data;

import android.provider.BaseColumns;

public class DbContract {

    public static class MovieTable implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_YEAR = "releaseYear";
        public static final String COLUMN_GENRE = "genre";

    }
}
