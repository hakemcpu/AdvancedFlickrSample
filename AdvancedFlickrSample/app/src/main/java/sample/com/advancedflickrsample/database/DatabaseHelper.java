package sample.com.advancedflickrsample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sample.com.advancedflickrsample.database.dbHandlers.AlbumsDbHandler;

/**
 * Created by hzaied on 4/3/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flickr.db";
    private static final int DB_VERSION = 1;

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

    public AlbumsDbHandler getAlbumsDbHandler() {
        return mAlbumsDbHandler;
    }
}
