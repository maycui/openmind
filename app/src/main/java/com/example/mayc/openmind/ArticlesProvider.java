package com.example.mayc.openmind;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.example.mayc.openmind.DatabaseHandler;
import com.example.mayc.openmind.ArticlesTable;

import java.util.HashMap;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.mayc.openmind.DatabaseHandler.DATABASE_NAME;

/**
 * Created by elliecorbus on 7/19/17.
 */

public class ArticlesProvider extends ContentProvider {

    // database
    private DatabaseHandler database;

    // used for the UriMatcher
    static final int ARTICLES = 1;
    static final int ARTICLE_ID = 2;

    // authority statement
    static final String AUTHORITY = "com.example.mayc.openmind.ArticlesProvider"; // a.k.a. authority

    private static final String BASE_PATH = "articles";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);


//    TODO: figure out what STUDENTS and STUDENT_ID are
//    static final int STUDENTS = 1;
//    static final int STUDENT_ID = 2;



    // TODO: Figure out how to handle URI IDs (what should it be?)
    // What exactly is this section doing?

    private static final UriMatcher uriMatcher = new UriMatcher(
            UriMatcher.NO_MATCH
    );
    static{
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ARTICLES); // Isn't the int key optional? How to fix this?
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ARTICLE_ID);
    }

    /**
     * Database specific constant declarations
     */

//    private SQLiteDatabase db;
//    static final String DATABASE_NAME = "ArticleDatabase";
//    static final String ARTICLES_TABLE_NAME = "articles";
//    static final int DATABASE_VERSION = 1;
//    // Creating the DB table is already done in the DB Handler...how to deal with this?
//    static final String CREATE_DB_TABLE =
//            " CREATE TABLE " + ARTICLES_TABLE_NAME +
//                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    " name TEXT NOT NULL, " +
//                    " grade TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */


    @Override
    public boolean onCreate() {

        database = new DatabaseHandler(getContext(), DATABASE_NAME);

        // return false if the database is null
        return (database == null)? false:true;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = uriMatcher.match(uri);

        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case ARTICLES:
                id = sqlDB.insert(Ar)





//
//
//        /**
//         * Add a new student record
//         */
//        long rowID = db.insert(ARTICLES_TABLE_NAME, "", values);
//
//        /**
//         * If record is added successfully
//         */
//        if (rowID > 0) {
//            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
//            getContext().getContentResolver().notifyChange(_uri, null);
//            return _uri;
//        }
//
//        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // set the table (setTables sets the list of tables to query)
        qb.setTables(ARTICLES_TABLE_NAME);

        // TODO: figure out this section
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case ARTICLES:
                break;

            case ARTICLE_ID:
                // adding the ID to the original query
                // TODO: figure out this line
                qb.appendWhere( _ID + "=" + uri.getLastPathSegment().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}














}