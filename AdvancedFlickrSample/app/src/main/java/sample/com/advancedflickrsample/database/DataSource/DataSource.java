package sample.com.advancedflickrsample.database.DataSource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sample.com.advancedflickrsample.database.DatabaseHelper;

/**
 * Created by hzaied on 4/3/15.
 */
public abstract class DataSource {

    protected DatabaseHelper mDbHelper;
    protected SQLiteDatabase mDatabase;

    protected DataSource(Context context) {
        this.mDbHelper = new DatabaseHelper(context);
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }
}
