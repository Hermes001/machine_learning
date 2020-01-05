package com.socket.pad.paddemo.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import androidx.core.database.DatabaseUtilsCompat;

public class DBProvider extends ContentProvider {

    private DatabaseHelper mDbHelper;
    private static final UriMatcher URI_MATCHER;
    private static final int CONFIGURE_TABLE_ID = 1;
    private static final int DATA_TABLE_ID = 2;
    private static final String UNKNOWN_URI_LOG = "Unknown URI ";

    static {
        // Create and initialize URI matcher.
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(DBManager.AUTHORITY, DBManager.CONFIGURE_TABLE_NAME, CONFIGURE_TABLE_ID);
        URI_MATCHER.addURI(DBManager.AUTHORITY,DBManager.DATA_TABLE_NAME,DATA_TABLE_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper  = new DatabaseHelper(getContext());
        return true;
    }

    
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String finalSortOrder = sortOrder;
        String[] finalSelectionArgs = selectionArgs;
        String finalGrouping = null;
        String finalHaving = null;
        switch (URI_MATCHER.match(uri)) {
            case CONFIGURE_TABLE_ID:
                qb.setTables(DBManager.CONFIGURE_TABLE_NAME);
                break;
            case DATA_TABLE_ID:
                qb.setTables(DBManager.DATA_TABLE_NAME);
                break;
            default:
                throw new RuntimeException("unknown uri " + uri.toString());
        }
        Cursor c = qb.query(db, projection, selection, finalSelectionArgs, finalGrouping, finalHaving,
                finalSortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    
    @Override
    public String getType( Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CONFIGURE_TABLE_ID:
                return DBManager.CONFIGURE_CONTENT_TYPE;
            case DATA_TABLE_ID:
                return DBManager.DATA_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException(UNKNOWN_URI_LOG + uri);
        }
    }

    
    @Override
    public Uri insert( Uri uri,  ContentValues initialValues) {
        String matchedTable = null;
        Uri baseInsertedUri = null;
        switch (URI_MATCHER.match(uri)) {
            case CONFIGURE_TABLE_ID:
                matchedTable = DBManager.CONFIGURE_TABLE_NAME;
                baseInsertedUri = DBManager.CONFIGURE_ID_URI_BASE;
                break;
            case DATA_TABLE_ID:
                matchedTable = DBManager.DATA_TABLE_NAME;
                baseInsertedUri = DBManager.DATA_ID_URI_BASE;
                break;
            default:
                break;
        }

        if (matchedTable == null) {
            throw new IllegalArgumentException(UNKNOWN_URI_LOG + uri);
        }

        ContentValues values;

        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            long rowId = db.insert(matchedTable, null, values);
            if (rowId >= 0) {
                // TODO : for inserted account register it here
                Uri retUri = ContentUris.withAppendedId(baseInsertedUri, rowId);
                getContext().getContentResolver().notifyChange(retUri, null);
                return retUri;
            }
        }catch (Exception e){
            e.printStackTrace();
        };
        // If the insert succeeded, the row ID exists.
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String finalWhere;
        int count = 0;
        Uri retUri = uri;
        switch (URI_MATCHER.match(uri)) {
            case DATA_TABLE_ID:
                count = db.delete(DBManager.DATA_TABLE_NAME, selection, selectionArgs);
                break;
            /*case FOOD_MANAGER_ID:
                finalWhere = DatabaseUtilsCompat.concatenateWhere("id" + " = " + ContentUris.parseId(uri), selection);
                count = db.delete(DBManager.FOOD_MANAGER_TABLE_NAME, finalWhere, selectionArgs);
                break;
            case FOOD_ADD_MANAGER:
                count = db.delete(DBManager.ADDED_FOOD_MANAGER_TABLE_NAME, selection, selectionArgs);
                break;
            case FOOD_ADD_MANAGER_ID:
                finalWhere = DatabaseUtilsCompat.concatenateWhere("id" + " = " + ContentUris.parseId(uri), selection);
                count = db.delete(DBManager.ADDED_FOOD_MANAGER_TABLE_NAME, finalWhere, selectionArgs);
                break;*/
            default:
                throw new IllegalArgumentException(UNKNOWN_URI_LOG + uri);
        }
        getContext().getContentResolver().notifyChange(retUri, null);
        return count;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
