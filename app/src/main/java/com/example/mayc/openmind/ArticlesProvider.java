package com.example.mayc.openmind;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.mayc.openmind.ArticlesTable.TABLE_NAME;
import static com.example.mayc.openmind.DatabaseHandler.DATABASE_NAME;

/**
 * Created by elliecorbus on 7/19/17.
 */

public class ArticlesProvider extends ContentProvider {

    // database
    public DatabaseHandler databaseHandler;
    public Context context;

    //these are codes that are returned when the URI is matched by the UriMatcher
    static final int ARTICLES = 1;
    static final int ARTICLE_ID = 2;

    // authority statement
    static final String AUTHORITY = "com.example.mayc.openmind.ArticlesProvider"; // a.k.a. authority

    private static final String BASE_PATH = "articles";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);


    //initiating a new Uri Matcher; UriMatcher is a utility class that can help match URIS in content providers
    private static final UriMatcher uriMatcher = new UriMatcher(
            UriMatcher.NO_MATCH
    );

    //this is a static initialization block (static version of a constructor) this block of code gets called whenever a class is constructed
    static {
        //paramters of .addURI is String authority, String path, and int code to return when article is matched
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ARTICLES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ARTICLE_ID);
    }


    @Override
    public boolean onCreate() {
        //TODO: understand why database becomes null when calling bulkInsert
        databaseHandler = new DatabaseHandler(getContext(), DATABASE_NAME);
        context = getContext();
        // return false if the database is null
        return databaseHandler != null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        //TODO: check for duplicate articles before inserting into table

        int numInserted = 0;
        String table;

        int uriType = uriMatcher.match(uri);

        switch (uriType) {
            case ARTICLES:
                table = TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase sqlDB = databaseHandler.getWritableDatabase();
        sqlDB.beginTransaction();
        try {
            for (ContentValues cv : values) {
                long newID = sqlDB.insertOrThrow(table, null, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
        }sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            sqlDB.endTransaction();
        }
        Log.d("OpenMind", "logging bulkInsert. Rows returned: " + numInserted + " uri=" + uri);
        return numInserted;
    }


    @Override
    public Cursor query(Uri uri, String[] projection,
                String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // set the table (setTables sets the list of tables to query)
        qb.setTables(TABLE_NAME);

        //.match() will return the correct code (Articles) if it finds a match
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            //if we got the code ARTICLES...
            case ARTICLES:
                break;
            //if we got the code ARTICLE_ID...
            case ARTICLE_ID:
                // adding the ID to the original query
                qb.appendWhere(_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d("OpenMind", "query " + uri + " uriType=" + uriType);

        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values,
                String selection, String[] selectionArgs) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return uri;
    }
}
