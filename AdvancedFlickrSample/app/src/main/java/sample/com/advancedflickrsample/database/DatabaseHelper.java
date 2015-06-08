package sample.com.advancedflickrsample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sample.com.advancedflickrsample.database.dbHandlers.AlbumsDbHandler;

/**
 * Class to manage the database and the operations done on it.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flickr.db";
    private static final int DB_VERSION = 1;

    protected SQLiteDatabase mDatabase;

    private AlbumsDbHandler mAlbumsDbHandler;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        mAlbumsDbHandler = new AlbumsDbHandler();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mAlbumsDbHandler.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mAlbumsDbHandler.onUpgrade(db, oldVersion, newVersion);
    }

    public void openDb() {
        mDatabase = getWritableDatabase();
    }

    public void closeDb() {
        mDatabase.close();
        close();
    }

    public AlbumsDbHandler getAlbumsDbHandler() {
        return mAlbumsDbHandler;
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        return mDatabase.insertWithOnConflict(table, nullColumnHack, initialValues, conflictAlgorithm);
    }

    public void execSQL(String query) {
        mDatabase.execSQL(query);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDatabase.rawQuery(sql, selectionArgs);
    }
}
