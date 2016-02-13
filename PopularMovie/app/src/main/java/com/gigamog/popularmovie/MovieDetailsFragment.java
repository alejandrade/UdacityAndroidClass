package com.gigamog.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamog.popularmovie.beans.PosterImageItem;
import com.gigamog.popularmovie.com.gigamog.popularmovie.constant.PersonalIntent;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);
        Intent intent = getActivity().getIntent();
        PosterImageItem poster = null;
        if(intent.hasExtra(PersonalIntent.getMOVIEDETAIL())){
            poster = intent.getParcelableExtra(PersonalIntent.getMOVIEDETAIL());
        }
        if(poster != null) {
            TextView title = (TextView) root.findViewById(R.id.labelTitle);
            TextView originalTitle = (TextView) root.findViewById(R.id.textOriginalTitle);
            TextView userRating = (TextView) root.findViewById(R.id.textRating);
            TextView release = (TextView) root.findViewById(R.id.textRelease);
            TextView synposis = (TextView) root.findViewById(R.id.textSynopsis);
            ImageView posterImage = (ImageView) root.findViewById(R.id.moviePoster);

            title.setText(poster.getName());
            originalTitle.setText(poster.getOriginal_title());
            userRating.setText((poster.getVote_average()*10) + "%");
            release.setText(poster.getRelease_date());
            synposis.setText(poster.getOverview());
            Log.v("poster URL",poster.getPoster_path() );
           Picasso.with(this.getContext()).load(poster.getPoster_path()).into(posterImage);
        }

        return root;
    }
}
