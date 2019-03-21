package com.example.jessi.moviedatabase;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieListActivity extends AppCompatActivity implements GetPopularMovies.AsyncResponse {

    MovieAdapter movieAdapter;
    ArrayList<Result> results;
    ListView movieList;
    SwipeRefreshLayout swipeRefreshLayout = null;
    //paging variables
    boolean moviesLoading = false;
    boolean listFull = false;
    int preLast = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.popular_movies_title);
        setContentView(R.layout.activity_movie_list);

        fetchMovies();
        setSwipeRefreshListener();
        setPagingListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        results = new ArrayList<>();
        preLast = 0;
        page = 1;
        setSwipeRefreshListener();
        setPagingListener();
    }

    public void fetchMovies(){
        GetPopularMovies getPopularMovies = new GetPopularMovies(page++);
        getPopularMovies.delegate = this;
        getPopularMovies.execute();
    }

    public void setSwipeRefreshListener(){
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                results.clear();
                fetchMovies();
            }
        });
    }

    public void setPagingListener(){
        preLast = 0;
        movieList = findViewById(R.id.list_movies);
        movieList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem >= totalItemCount - 10){
                    if (preLast != lastItem && !moviesLoading && !listFull){
                        moviesLoading = true;
                        fetchMovies();
                    }
                    preLast = lastItem;
                }
            }
        });
    }

    @Override
    public void processFinish(PopularMoviesModel popularMovies) {
        for (int i = 0; i < popularMovies.getResults().length; i++){
            results.add(popularMovies.getResults()[i]);
        }

        if (movieAdapter == null){
            movieAdapter = new MovieAdapter(MovieListActivity.this, R.layout.list_movie_item, results);
            movieList.setAdapter(movieAdapter);
        }
        else movieAdapter.notifyDataSetChanged();

        movieList = findViewById(R.id.list_movies);
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MovieListActivity.this, MovieOverviewActivity.class);
                intent.putExtra("details", (Serializable) results.get(position));
                startActivity(intent);
            }
        });

        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

        moviesLoading = false;
        if (page == popularMovies.getTotalPages()){
            listFull = true;
        }
    }
}
