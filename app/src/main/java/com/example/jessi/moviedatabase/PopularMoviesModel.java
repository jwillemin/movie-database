package com.example.jessi.moviedatabase;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PopularMoviesModel {
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    private Result[] results;

    public Result[] getResults() {
        return results;
    }
}

class Result {
    @SerializedName("vote_count")
    private int voteCount;
    private int id;
    private boolean video;
    @SerializedName("vote_average")
    private double voteAverage;
    private String title;
    private double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    private int[] genreIds;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private boolean adult;
    private String overview;
    @SerializedName("release_date")
    private Date releaseDate;

    public String getTitle() {return title;}
    public Date getReleaseDate() {return releaseDate;}
    public String getPosterPath() {return  posterPath;}
}
