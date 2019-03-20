package com.example.jessi.moviedatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        viewHolder.title.setText(result.getTitle());

        return view;
    }

    private class ViewHolder{
        TextView title;
    }

}
