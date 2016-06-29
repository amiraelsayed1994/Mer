package com.example.amira.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.amira.myapplication.Data.Movie;
import com.example.amira.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amira on 4/6/2016.
 */
public class GridAdapter extends BaseAdapter {



    private Context mContext;
    ArrayList<Movie> movies;

    public GridAdapter(Context c, ArrayList<Movie> paths) {
        mContext = c;
        this.movies = paths;
    }

    public int getCount() {
        return movies.size();
    }

    public Object getItem(int position) {
        return movies.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.griditem, parent, false);
            holder.poster = (ImageView) convertView.findViewById(R.id.grid_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500"+
                movies.get(position).getPoster())
                .into(holder.poster);

        return convertView;
    }

    private class ViewHolder {
        ImageView poster;
    }


}
