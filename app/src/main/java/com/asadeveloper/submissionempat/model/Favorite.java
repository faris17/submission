package com.asadeveloper.submissionempat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String date;
    private String poster;
    private String vote;
    private String kategori;

    public Favorite(){}
    public Favorite(int id, String title, String overview, String date, String poster, String vote, String kategori) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.date = date;
        this.poster = poster;
        this.vote = vote;
        this.kategori = kategori;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(date);
        dest.writeString(poster);
        dest.writeString(vote);
        dest.writeString(kategori);
    }

    private Favorite(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        date = in.readString();
        poster = in.readString();
        vote = in.readString();
        kategori = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
