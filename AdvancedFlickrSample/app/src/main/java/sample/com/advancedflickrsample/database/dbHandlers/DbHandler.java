package sample.com.advancedflickrsample.database.dbHandlers;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hzaied on 4/3/15.
 */
public abstract class DbHandler {

    public abstract String getCreateQuery();
    public abstract String getDropQuery();

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateQuery());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getDropQuery());
        onCreate(db);
    }
}
