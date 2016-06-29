package com.example.amira.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by amira on 4/28/2016.
 *
 */
public class Database extends SQLiteOpenHelper {
    static String name = "Movie_db";
    SQLiteDatabase db;
    Context context;
    public Database(Context context) {
        super(context, name, null, 3);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Moves (id integer primary key,poster text,title text,overview text,vote_average integer,relasedate text,movie_id integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Moves");
        onCreate(db);
    }

    public void add(String poster, String title, String overview, String releasedate, String rate, int id) {
        ContentValues content = new ContentValues();
        content.put("poster", poster);
        content.put("title", title);
        content.put("overview", overview);
        content.put("vote_average", rate);
        content.put("relasedate", releasedate);
        content.put("movie_id", id);
        db = getWritableDatabase();
        long i = db.insert("Moves", null, content);
        if(i == 0)
            Toast.makeText(context, "not inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, " inserted", Toast.LENGTH_SHORT).show();

        db.close();

    }

    public Boolean ifexist(String Movietitle) {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Moves where title=?", new String[]{Movietitle});
        if (cursor.getCount() != 0) {
            db.close();
            return true;
        }
        return false;
    }

    public void delete_movie(String Movietitle) {
        db = getWritableDatabase();
        db.delete("Moves", "title like ?", new String[]{Movietitle});
        db.close();

    }

    public Cursor Fetch_all() {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Moves", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

        } else {
            cursor = null;
        }
        db.close();
        return cursor;
    }

}

