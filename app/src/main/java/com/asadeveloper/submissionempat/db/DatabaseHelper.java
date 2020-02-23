package com.asadeveloper.submissionempat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.asadeveloper.submissionempat.db.DatabaseContract.TABLE_NAME;

import com.asadeveloper.submissionempat.db.DatabaseContract.NoteColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbfavorite";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)" ,
            TABLE_NAME,
            NoteColumns._ID,
            NoteColumns.TITLE,
            NoteColumns.OVERVIEW,
            NoteColumns.DATE,
            NoteColumns.POSTER,
            NoteColumns.VOTE,
            NoteColumns.KATEGORI
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
