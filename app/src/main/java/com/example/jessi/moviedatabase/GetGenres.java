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

public class GetGenres extends AsyncTask<Void, Void, GenreModel> {

    public static final String GENRE_LIST_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key=401aa5eca4619ade84c90fd075e22588";

    public GetGenres.AsyncResponse delegate = null;

    public GetGenres() { }

    @Override
    protected GenreModel doInBackground(Void... voids) {

        String response = null;
        GenreModel genreModel = new GenreModel();

        try{
            URL url = new URL(GENRE_LIST_URL);

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
                genreModel = new Gson().fromJson(response, GenreModel.class);

            }
            else{
                Log.d("Error", "The code is " + responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return genreModel;
    }

    @Override
    protected void onPostExecute(GenreModel genres) {
        delegate.processFinish(genres);
    }

    public interface AsyncResponse{
        void processFinish(GenreModel genres);
    }

}
