package com.example.amira.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.amira.myapplication.DetailsData.Review;
import com.example.amira.myapplication.DetailsData.Trailer;
import com.example.amira.myapplication.InterFace.Type;
import com.example.amira.myapplication.R;

import java.util.ArrayList;

/**
 * Created by amira on 4/23/2016.
 */
public class ListAdpater extends BaseAdapter {

    private Context mContext;
    private ArrayList<Type> items;

    public  enum choosetype {
        TRAILER_ITEM, REVIEW_ITEM
    }

    public ListAdpater(Context context, ArrayList<Type> items) {
        this.mContext = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Type getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).get_Type();
    }

    @Override
    public int getViewTypeCount() {
        return choosetype.values().length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            if (rowType == choosetype.TRAILER_ITEM.ordinal()) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.trailer_list, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.tv_trailer);
            } else {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.reiview_list, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.tv_review);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (rowType == choosetype.REVIEW_ITEM.ordinal())
            holder.textView.setText(((Review) items.get(position)).getContent());
        else
            holder.textView.setText(((Trailer) items.get(position)).getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }



}



