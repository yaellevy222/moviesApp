package com.example.moviesapp.data;

import com.example.moviesapp.Movie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

  public class DbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "movies.db";
        SQLiteDatabase db;

        private static final String TAG = DbHelper.class.getSimpleName();
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DbContract.MovieTable.TABLE_NAME + " (" +
                        DbContract.MovieTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DbContract.MovieTable.COLUMN_TITLE + " TEXT UNIQUE NOT NULL, " +
                        DbContract.MovieTable.COLUMN_IMAGE + " TEXT NOT NULL, " +
                        DbContract.MovieTable.COLUMN_RATING + " TEXT NOT NULL, " +
                        DbContract.MovieTable.COLUMN_RELEASE_YEAR + " TEXT NOT NULL, " +
                        DbContract.MovieTable.COLUMN_GENRE + " TEXT NOT NULL " + " );";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DbContract.MovieTable.TABLE_NAME;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            db = this.getWritableDatabase();

        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
            Log.d(TAG, "Database Created Successfully");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        public void readDataToDb(String jsonStr) throws IOException, JSONException {

            try {
                JSONArray jsonarray = new JSONArray(jsonStr);

                // looping through all movies
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject m = jsonarray.getJSONObject(i);
                    String title = m.getString("title");
                    String image = m.getString("image");
                    String rating = m.getString("rating");
                    String releaseYear = m.getString("releaseYear");
                    JSONArray genreArray = m.getJSONArray("genre");

                    StringBuilder sb = new StringBuilder();
                    // looping through all genres
                    for (int j = 0; j < genreArray.length(); j++) {
                        sb.append(genreArray.getString(j));
                        if (j<genreArray.length()-1)
                        {
                            sb.append(", ");
                        }
                    }

                    String genre = sb.toString();
                    ContentValues movieValues = new ContentValues();

                    movieValues.put(DbContract.MovieTable.COLUMN_TITLE, title);
                    movieValues.put(DbContract.MovieTable.COLUMN_IMAGE, image);
                    movieValues.put(DbContract.MovieTable.COLUMN_RATING, rating);
                    movieValues.put(DbContract.MovieTable.COLUMN_RELEASE_YEAR, releaseYear);
                    movieValues.put(DbContract.MovieTable.COLUMN_GENRE, genre);

                    db.insert(DbContract.MovieTable.TABLE_NAME, null, movieValues);
                    Log.d(TAG, "Inserted Successfully " + movieValues);
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        }

        public ArrayList<Movie> readDataFromFile() throws IOException {
            db = this.getWritableDatabase();

            String[] projection = {
                    DbContract.MovieTable.COLUMN_TITLE,
                    DbContract.MovieTable.COLUMN_IMAGE,
                    DbContract.MovieTable.COLUMN_RATING,
                    DbContract.MovieTable.COLUMN_RELEASE_YEAR,
                    DbContract.MovieTable.COLUMN_GENRE
            };

            String sortOrder =
                    DbContract.MovieTable.COLUMN_RELEASE_YEAR + " DESC";

            Cursor cursor = db.query(
                    DbContract.MovieTable.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );

            ArrayList<Movie> moviesList = new ArrayList<Movie>();
            while(cursor.moveToNext()) {
                String title = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.MovieTable.COLUMN_TITLE));
                String image = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.MovieTable.COLUMN_IMAGE));
                String rating = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.MovieTable.COLUMN_RATING));
                String releaseYear = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.MovieTable.COLUMN_RELEASE_YEAR));
                String genre = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.MovieTable.COLUMN_GENRE));

                Movie m = new Movie (title, image, rating, releaseYear, genre);
                moviesList.add(m);
            }
            cursor.close();

            return moviesList;
        }
    }