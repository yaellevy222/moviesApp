package com.example.moviesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;


public class MovieActivity  extends AppCompatActivity {

    private static final String TAG = HttpHandler.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent intent = getIntent();
        Movie movie = (Movie) getIntent().getParcelableExtra("movie_obj");

        // display movie title
        final TextView titleView = (TextView) findViewById(R.id.movieTitle);
        titleView.setText(movie.getTitle());

        // display movie image
        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(movie.getImage());

        // display movie rating
        final TextView ratingView = (TextView) findViewById(R.id.movieRating);
        ratingView.setText("Rating: " + movie.getRating());

        // display movie release year
        final TextView releaseView = (TextView) findViewById(R.id.movieRelease);
        releaseView.setText("Release Year: " + movie.getReleaseYear());

        // display movie genre
        final TextView genreView = (TextView) findViewById(R.id.movieGenre);
        genreView.setText("Genre: " + movie.getGenre());
    }

    // This method will be invoked when user click android device Back menu at bottom.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("message_return", "This data is returned when user click back menu in target activity.");
        setResult(RESULT_OK, intent);
        finish();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

