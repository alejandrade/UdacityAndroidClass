package com.gigamog.popularmovie.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alejandor on 2/7/2016.
 */
public class PosterImageItem implements Parcelable {



    String poster_path;
    String name;
    String overview;
    String original_title;
    String backdrop_path;
    String release_date;
    double popularity, vote_average;
    int vote_count;

    public PosterImageItem(String poster_path,
                           String name,
                           String overview,
                           String original_title,
                           String backdrop_path,
                           double popularity,
                           double vote_average,
                           int vote_count,
                           String release_date) {
        this.poster_path = poster_path;
        this.name = name;
        this.overview = overview;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.release_date = release_date;
    }

    public PosterImageItem(Parcel in){
        this.poster_path = in.readString();
        this.name = in.readString();
        this.overview = in.readString();
        this.original_title = in.readString();
        this.backdrop_path = in.readString();
        this.popularity = in.readDouble();
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
        this.release_date = in.readString();
    }


    @Override
    public void writeToParcel(Parcel par, int i) {
        par.writeString(poster_path);
        par.writeString(name);
        par.writeString(overview);
        par.writeString(original_title);
        par.writeString(backdrop_path);
        par.writeDouble(popularity);
        par.writeDouble(vote_average);
        par.writeFloat(vote_count);
        par.writeString(release_date);

    }
    @Override
    public int describeContents() {
        return 0;
    }
    public final static Parcelable.Creator<PosterImageItem> CREATOR = new Parcelable.Creator<PosterImageItem>() {
        @Override
        public PosterImageItem createFromParcel(Parcel parcel) {
            return new PosterImageItem(parcel);
        }

        @Override
        public PosterImageItem[] newArray(int i) {
            return new PosterImageItem[i];
        }

    };


    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {

        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


}
