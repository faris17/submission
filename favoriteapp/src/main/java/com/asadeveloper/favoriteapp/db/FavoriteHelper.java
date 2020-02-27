package com.asadeveloper.favoriteapp.db;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.asadeveloper.favoriteapp.MovieItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.asadeveloper.favoriteapp.db.DatabaseContract.CONTENT_URI;
import static com.asadeveloper.favoriteapp.db.DatabaseContract.NoteColumns.DATE;
import static com.asadeveloper.favoriteapp.db.DatabaseContract.NoteColumns.OVERVIEW;
import static com.asadeveloper.favoriteapp.db.DatabaseContract.NoteColumns.POSTER;
import static com.asadeveloper.favoriteapp.db.DatabaseContract.NoteColumns.TITLE;

public class FavoriteHelper {

    Context mContext;
    private final WeakReference<Context> weakContext;

    public FavoriteHelper() {
        weakContext = new WeakReference<>(mContext);
        mContext = weakContext.get();
    }

    public ArrayList<MovieItems> getAllMovie() {

        Uri uri;
        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<MovieItems> arrayList = new ArrayList<>();
        MovieItems favMovie;
        if (cursor.getCount() > 0) {
            do {
                favMovie = new MovieItems();
                favMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favMovie.setOriginal_title(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favMovie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favMovie.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favMovie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(favMovie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
}
