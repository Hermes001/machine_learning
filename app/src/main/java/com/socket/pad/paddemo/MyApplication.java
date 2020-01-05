package com.socket.pad.paddemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.socket.pad.paddemo.db.DatabaseHelper;

public class MyApplication extends Application {

    private DatabaseHelper mDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
    }



}
