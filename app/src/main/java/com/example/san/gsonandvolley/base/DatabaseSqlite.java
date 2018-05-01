package com.example.san.gsonandvolley.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.san.gsonandvolley.model.movie.Movie;
import com.example.san.gsonandvolley.model.movie.Search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSqlite {
    SQLiteDatabase database;
    private Context context;

    public DatabaseSqlite(Context context) {
        this.context = context;
    }

    public void openDatabase() {
        try {
            // path to the external SD card (something like: /storage/sdcard/...)
            // String storagePath = Environment.getExternalStorageDirectory().getPath();

            // path to internal memory file system (data/data/cis470.matos.databases)
            File storagePath = context.getFilesDir();

            String myDbPath = storagePath + "/" + "DBMovie";


            database = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);

        } catch (SQLiteException e) {

            e.printStackTrace();
        }

        database.beginTransaction();
        try {
            // create table
            database.execSQL("create table Movie("
                    + " id integer PRIMARY KEY autoincrement, "
                    + " title  text, imdbID text, type text, poster text,year text );");
            // commit your changes
            database.setTransactionSuccessful();

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void insetMovie(Search movie) {

        database.beginTransaction();
        try {

            // insert rows
            ContentValues args = new ContentValues();
            args.put("title", movie.getTitle());
            args.put("imdbID", movie.getImdbID());
            args.put("type", movie.getType());
            args.put("year", movie.getYear());
            database.insert("Movie", null, args);
            // commit your changes
            database.setTransactionSuccessful();

        } catch (SQLiteException e2) {
            e2.printStackTrace();
        } finally {
            database.endTransaction();
        }

    }

    public List<Search> getListMovieByTitle(String title) {
        List<Search> movies = new ArrayList<>();
        Cursor cursor = null;
        try {
            // hard-coded SQL select with no arguments
            String mySQL = "select * from Movie where title like '%" + title + "%';";
            cursor = database.rawQuery(mySQL, null);

            // get the first recID
            cursor.moveToFirst();
            do {
                movies.add(new Search(cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("year")),
                        cursor.getString(cursor.getColumnIndex("imdbID")),
                        cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getString(cursor.getColumnIndex("poster"))));
            } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return movies;
    }
}
