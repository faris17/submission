package com.asadeveloper.favoriteapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "favorite";
    public static final String AUTHORITY = "com.asadeveloper.submissionempat";
    private static final String SCHEME = "content";

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

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
}
