package com.example.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String title;
    private String image;
    private String rating;
    private String releaseYear;
    private String genre;

    public Movie(String title, String image, String rating, String releaseYear, String genre) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    // Parcelling part
    public Movie(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.title = data[0];
        this.image = data[1];
        this.rating = data[2];
        this.releaseYear = data[3];
        this.genre = data[4];
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.title,
                this.image,
                this.rating,
                this.releaseYear,
                this.genre});
    }

    public int describeContents(){
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() { return image; }

    public String getRating(){
        return rating;
    }

    public String getReleaseYear(){
        return releaseYear;
    }

    public String getGenre(){
        return genre;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
