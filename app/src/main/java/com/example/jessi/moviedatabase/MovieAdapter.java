package com.example.jessi.moviedatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Result>{

    Context context;
    MovieAdapter.ViewHolder viewHolder = null;
    int resource = 0;

    public MovieAdapter(Context context, int resource, ArrayList<Result> popularMovies) {
        super(context, resource, popularMovies);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_movie_item, null);
        } else {
            view = convertView;
        }

        Result result = getItem(position);

        viewHolder = new MovieAdapter.ViewHolder();
        viewHolder.title = view.findViewById(R.id.title);
        viewHolder.releaseDate = view.findViewById(R.id.release_date);
        viewHolder.poster = view.findViewById(R.id.poster);

        viewHolder.title.setText(result.getTitle());
        viewHolder.releaseDate.setText(new SimpleDateFormat("MMMM d, yyyy").format(result.getReleaseDate()));
        Picasso.get().load("https://image.tmdb.org/t/p/w154" + result.getPosterPath()).into(viewHolder.poster);

        return view;
    }

    private class ViewHolder{
        TextView title;
        TextView releaseDate;
        ImageView poster;
    }

}
