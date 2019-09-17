package com.example.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Button;
import android.content.Intent;

class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Movie m = (Movie) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView release = (TextView) convertView.findViewById(R.id.release_year);

        // Populate the data into the template view using the data object
        title.setText(m.getTitle());
        release.setText("Release Year: " + m.getReleaseYear());

        Button movieButton = (Button) convertView.findViewById(R.id.btn);
        movieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start MovieActivity.class
                Intent myIntent = new Intent(getContext(), MovieActivity.class);
                myIntent.putExtra("movie_obj", m);
                getContext().startActivity(myIntent);
            }});
        // Return the completed view to render on screen
        return convertView;
    }
}
