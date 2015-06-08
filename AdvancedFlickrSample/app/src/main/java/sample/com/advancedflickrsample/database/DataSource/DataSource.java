package sample.com.advancedflickrsample.database.DataSource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sample.com.advancedflickrsample.database.DatabaseHelper;

/**
 * Abstract data source class for the manging the database for different data source types.
 */
public abstract class DataSource {

    protected DatabaseHelper mDbHelper;

    protected DataSource(Context context) {
        this.mDbHelper = new DatabaseHelper(context);
    }

    public void open() {
        mDbHelper.openDb();
    }

    public void close() {
        mDbHelper.closeDb();
    }
}
