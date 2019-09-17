/*package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.moviesapp.data.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private DbHelper DbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper dbHelper = new DbHelper(this);
    }

  }*/

package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import com.example.moviesapp.data.DbHelper;
import org.json.JSONException;
import java.util.ArrayList;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    String jsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.list);
        new GetMovies().execute();
    }

    private class GetMovies extends AsyncTask<Void, Void, Void> {

        private DbHelper dbHelper;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://api.androidhive.info/json/movies.json";
            String jsonStr = sh.makeServiceCall(url);
            this.dbHelper = new DbHelper(MainActivity.this);
            try {
                dbHelper.readDataToDb(jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            ArrayList<Movie> moviesList = null;

            try {
                moviesList = this.dbHelper.readDataFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setContentView(R.layout.activity_main);
            final ListView list = findViewById(R.id.list);
            MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, moviesList);
            list.setAdapter(movieAdapter);
        }
    }
}