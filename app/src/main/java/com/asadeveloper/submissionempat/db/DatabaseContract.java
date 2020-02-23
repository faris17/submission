package com.asadeveloper.submissionempat.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "favorite";
    public static final class NoteColumns implements BaseColumns {


        //Note title
        public static final String TITLE = "title";
        //Note description
        public static final String OVERVIEW = "overview";
        //Note date
        public static final String DATE = "date";
        //Note poster
        public static final String POSTER = "poster";
        //Note vote
        public static final String VOTE = "vote";
        //Note kategori
        public static final String KATEGORI = "kategori";

    }

    public static final String AUTHORITY = "com.asadeveloper.submissionempat";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
    }
}
