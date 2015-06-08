package sample.com.advancedflickrsample.content_providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import sample.com.advancedflickrsample.database.DatabaseHelper;
import sample.com.advancedflickrsample.database.dbHandlers.AlbumsDbHandler;

/**
 * Created by hzaied on 4/11/15.
 */
public class FlickrProvider extends ContentProvider {

    protected DatabaseHelper mDbHelper;

    private static final String AUTHORITY = "sample.com.advancedflickrsample.content_provider";

    private static final String BASE_PATH = "flickr";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/albums";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/album_item";

    private static final int ALBUMS = 10;
    private static final int ALBUM_ID = 20;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, ALBUMS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ALBUM_ID);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Check if all the colums are available.
        checkColumns(projection);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(AlbumsDbHandler.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ALBUMS:
                break;
            case ALBUM_ID:
                queryBuilder.appendWhere(AlbumsDbHandler.COL_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated;
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ALBUMS:
                rowsUpdated = database.update(AlbumsDbHandler.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ALBUM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = database.update(AlbumsDbHandler.TABLE_NAME, values,
                            AlbumsDbHandler.COL_ID + "=" + id, null);
                } else {
                    rowsUpdated = database.update(AlbumsDbHandler.TABLE_NAME, values,
                            AlbumsDbHandler.COL_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id;
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ALBUMS:
                id = database.insert(AlbumsDbHandler.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ALBUMS:
                rowsDeleted = database.delete(AlbumsDbHandler.TABLE_NAME, selection, selectionArgs);
                break;
            case ALBUM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = database.delete(AlbumsDbHandler.TABLE_NAME, AlbumsDbHandler.COL_ID + "=" + id, null);
                } else {
                    rowsDeleted = database.delete(AlbumsDbHandler.TABLE_NAME,
                            AlbumsDbHandler.COL_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    private void checkColumns(String[] projection) {
        String[] available = {AlbumsDbHandler.COL_ID, AlbumsDbHandler.COL_URL, AlbumsDbHandler.COL_TITLE,
                AlbumsDbHandler.COL_TIME};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
