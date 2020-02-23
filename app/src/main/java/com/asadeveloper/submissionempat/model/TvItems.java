package com.asadeveloper.submissionempat.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvItems implements Parcelable {
    private String poster_path;
    private String original_name;
    private String overview;
    private String first_air_date;
    private String vote_average;
    private int id;

    public TvItems(JSONObject object){
        try{
            String poster_path = "https://image.tmdb.org/t/p/w500"+object.getString("poster_path");
            String original_name = object.getString("original_name");
            String overview = object.getString("overview");
            String first_air_date = object.getString("first_air_date");
            String vote_average = object.getString("vote_average");
            int id = object.getInt("id");

            this.poster_path = poster_path;
            this.original_name = original_name;
            this.overview = overview;
            this.first_air_date = first_air_date;
            this.vote_average = vote_average;
            this.id = id;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public TvItems() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeString(this.original_name);
        dest.writeString(this.overview);
        dest.writeString(this.first_air_date);
        dest.writeString(this.vote_average);
        dest.writeInt(this.id);
    }

    protected TvItems(Parcel in) {
        this.poster_path = in.readString();
        this.original_name = in.readString();
        this.overview = in.readString();
        this.first_air_date = in.readString();
        this.vote_average = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<TvItems> CREATOR = new Creator<TvItems>() {
        @Override
        public TvItems createFromParcel(Parcel source) {
            return new TvItems(source);
        }

        @Override
        public TvItems[] newArray(int size) {
            return new TvItems[size];
        }
    };
}


