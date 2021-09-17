package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    // variables that store data on a movie
    String backdropPath; // backdrop poster presented in landscape style
    String posterPath; // main movie poster given in a portrait style
    String title;
    String overview;
    double vote_average; // the movie rating given by users
    int movieID;

    // empty constructor needed for Parcel library
    public Movie(){ }

    // constructor initializes data members with JSON data
    public Movie(JSONObject jsonObject) throws JSONException{
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        vote_average = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
    }

    public int getMovieID() {
        return movieID;
    }

    // static method that when called returns a list of movies and their data
    public  static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException{
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    @Override
    public String toString(){
        return this.title + " " + this.vote_average;
    }
}
