package com.example.jessi.moviedatabase;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetPopularMovies extends AsyncTask<Void, Void, PopularMoviesModel> {

    public static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?api_key=401aa5eca4619ade84c90fd075e22588";
    public static final String PAGE_STRING = "&page=";

    public AsyncResponse delegate = null;
    int page;

    public GetPopularMovies(int page) {
        this.page = page;
    }

    @Override
    protected PopularMoviesModel doInBackground(Void... voids) {

        String response = null;
        PopularMoviesModel popularMovies = new PopularMoviesModel();

        try{
            URL url = new URL(POPULAR_MOVIES_URL + PAGE_STRING + page);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }

                response = builder.toString();
                popularMovies = new Gson().fromJson(response, PopularMoviesModel.class);

            }
            else{
                Log.d("Error", "The code is " + responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return popularMovies;
    }

    @Override
    protected void onPostExecute(PopularMoviesModel popularMovies) {
        delegate.processFinish(popularMovies);
    }

    public interface AsyncResponse{
        void processFinish(PopularMoviesModel popularMovies);
    }
}
