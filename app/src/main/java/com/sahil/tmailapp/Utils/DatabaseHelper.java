package com.sahil.tmailapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ymg.db";
    private static final int DATABASE_VERSION = 1;

    // Define your table and column names
    private static final String TABLE_NAME = "user_credentials";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_TOKEN = "token";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_TOKEN + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades
    }

    public void insertUserCredentials(String email, String token) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_TOKEN, token);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getAllUserCredentials() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

}
