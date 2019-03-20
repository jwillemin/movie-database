package com.example.jessi.moviedatabase;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieListActivity extends AppCompatActivity implements GetPopularMovies.AsyncResponse {

    MovieAdapter movieAdapter;
    ArrayList<Result> results;
    SwipeRefreshLayout swipeRefreshLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Popular Movies");
        setContentView(R.layout.activity_movie_list);

        fetchMovies();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovies();
            }
        });
    }

    public void fetchMovies(){
        GetPopularMovies getPopularMovies = new GetPopularMovies();
        getPopularMovies.delegate = this;
        getPopularMovies.execute();
    }

    @Override
    public void proccessFinish(PopularMoviesModel popularMovies) {
        if (results == null){
            results = new ArrayList<>();
        } else results.clear();
        for (int i = 0; i < popularMovies.getResults().length; i++){
            results.add(popularMovies.getResults()[i]);
        }

        ListView movieList = findViewById(R.id.list_movies);
        if (movieAdapter == null){
            movieAdapter = new MovieAdapter(MovieListActivity.this, R.layout.list_movie_item, results);
            movieList.setAdapter(movieAdapter);
        }
        else movieAdapter.notifyDataSetChanged();

//        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(RisingStoriesActivity.this, WebActivity.class);
//                intent.putExtra("url", posts.get(position).getUrl());
//                startActivity(intent);
//            }
//        });

        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
