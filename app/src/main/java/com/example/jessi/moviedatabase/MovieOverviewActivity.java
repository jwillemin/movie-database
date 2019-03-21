package com.example.jessi.moviedatabase;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieOverviewActivity extends AppCompatActivity implements GetGenres.AsyncResponse {

    ArrayList<Genre> genreList;
    Result detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Movie Details");
        setContentView(R.layout.activity_movie_overview);

        fetchGenres();
        setView();
    }

    public void setView(){
        detail = (Result) getIntent().getExtras().get("details");

        ImageView poster = findViewById(R.id.poster);
        TextView title = findViewById(R.id.title);
        TextView overview = findViewById(R.id.overview);

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + detail.getPosterPath()).into(poster);
        title.setText(detail.getTitle());
        overview.setText(detail.getOverview());
    }

    public void fetchGenres(){
        GetGenres getGenres = new GetGenres();
        getGenres.delegate = this;
        getGenres.execute();
    }

    @Override
    public void proccessFinish(GenreModel genres) {
        if (genreList == null){
            genreList = new ArrayList<>();
        } else genreList.clear();
        for (int i = 0; i < genres.getGenres().length; i++){
            genreList.add(genres.getGenres()[i]);
        }

        TextView genreView = findViewById(R.id.genres);

        String genresTextList = "Genres: ";
        List genreNames = new ArrayList<String>();

        for (int i = 0; i < detail.getGenreIds().length; i++){
            for (int j = 0; j < genreList.size(); j++){
                if (detail.getGenreIds()[i] == genreList.get(j).getId()){
                    genreNames.add(genreList.get(j).getName());
                }
            }
        }
        genresTextList += TextUtils.join(", ", genreNames);
        genreView.setText(genresTextList);

    }
}
