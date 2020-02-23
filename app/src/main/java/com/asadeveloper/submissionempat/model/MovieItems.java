package com.asadeveloper.submissionempat.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.asadeveloper.submissionempat.db.DatabaseContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.DATE;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.OVERVIEW;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.POSTER;
import static com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns.TITLE;

public class MovieItems implements Parcelable{

    private String poster_path;
    private String original_title;
    private String overview;
    private String release_date;
    private String kategori;
    private int id;

    public MovieItems(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        this.original_title=cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
        this.overview=cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
        this.poster_path=cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
        this.release_date=cursor.getString(cursor.getColumnIndexOrThrow(DATE));


    }

    public MovieItems(JSONObject object){
        try{
            String poster_path = "https://image.tmdb.org/t/p/w500"+object.getString("poster_path");
            String original_title = object.getString("original_title");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");
            int id = object.getInt("id");

            this.poster_path = poster_path;
            this.original_title = original_title;
            this.overview = overview;
            this.release_date = release_date;
            this.id = id;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
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

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public MovieItems() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.kategori);
        dest.writeInt(this.id);
    }

    protected MovieItems(Parcel in) {
        this.poster_path = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.kategori = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}

