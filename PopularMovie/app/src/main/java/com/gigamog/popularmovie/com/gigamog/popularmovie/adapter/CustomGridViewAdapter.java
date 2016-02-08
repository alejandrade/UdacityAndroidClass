package com.gigamog.popularmovie.com.gigamog.popularmovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamog.popularmovie.R;
import com.gigamog.popularmovie.beans.PosterImageItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Alejandor on 2/7/2016.
 */
public class CustomGridViewAdapter extends ArrayAdapter<PosterImageItem> {
    Context context;
    public CustomGridViewAdapter(Context context, int resourceId, List<PosterImageItem> item){
        super(context, resourceId, item);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
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

        Log.v("Movie URL", posterItem.getPoster_path());
        Picasso.with(context).load(posterItem.getPoster_path()).placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.imageView);

        return convertView;
    }

}
