package com.gigamog.popularmovie.com.gigamog.popularmovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gigamog.popularmovie.R;
import com.gigamog.popularmovie.beans.PosterImageItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Alejandor on 2/7/2016.
 */
public class CustomGridViewAdapter extends ArrayAdapter<PosterImageItem> {
    Context context;
    boolean fillME = false;
    int position = -1;
    public CustomGridViewAdapter(Context context, int resourceId, List<PosterImageItem> item){
        super(context, resourceId, item);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
    }

    @Override
    public PosterImageItem getItem(int position) {
        if(this.getCount()/2 == position){
            fillME = true;
        }
        return super.getItem(position);
    }


    public boolean getFillME(){
        return fillME;
    }
    public void setFillME(Boolean stop){
        fillME = stop;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        PosterImageItem posterItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.movie_poster, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.moviePosterImage);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Picasso.with(context).load(posterItem.getPoster_path()).placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.imageView);

        return convertView;
    }

}
