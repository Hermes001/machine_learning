package com.socket.pad.paddemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CONFIGURE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + DBManager.CONFIGURE_TABLE_NAME
            + " ("
            + DBManager.CONFIGURE_SY_ID + " TEXT PRIMARY KEY,"
            + DBManager.CONFIGURE_CJ_NO + " TEXT,"
            + DBManager.CONFIGURE_CJ_SBNO + " TEXT,"
            + DBManager.CONFIGURE_CJ_TITLE + " TEXT,"
            + DBManager.CONFIGURE_CJ_IP + " TEXT,"
            + DBManager.CONFIGURE_CJ_PARA + " TEXT"
            + ");";

    public static final String TABLE_DATA_CREATE = "CREATE TABLE IF NOT EXISTS "
            + DBManager.DATA_TABLE_NAME
            + " ("
            + DBManager.DATA_XH + " TEXT PRIMARY KEY,"
            + DBManager.DATA_XN + " TEXT,"
            + DBManager.DATA_PRESSURE_NUM + " INTEGER,"
            + DBManager.DATA_COEFFICIENT + " INTEGER,"
            + DBManager.DATA_PERCENT_AVERAGE + " INTEGER,"
            + DBManager.DATA_PERCENTLIST + " TEXT,"
            + DBManager.DATA_STATUS + " INTEGER,"
            + DBManager.DATA_TIME + " INTEGER"
            + ");";


    public DatabaseHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context){
        super(context, DBManager.AUTHORITY,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.d("cfn","DatabaseHelper oncreate");
        db.execSQL(TABLE_CONFIGURE_CREATE);
        db.execSQL(TABLE_DATA_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.d("cfn","DatabaseHelper onUpgrade database from version "
                + oldVersion + " to " + newVersion);
    }

}
