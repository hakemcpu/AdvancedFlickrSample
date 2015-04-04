package sample.com.advancedflickrsample.database.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sample.com.advancedflickrsample.database.dbHandlers.AlbumsDbHandler;
import sample.com.advancedflickrsample.entities.AlbumItem;

/**
 * Created by hzaied on 4/3/15.
 */
public class AlbumsDataSource extends DataSource {


    public AlbumsDataSource(Context context) {
        super(context);
    }

    public AlbumItem createAlbum(String url, String title) {
        ContentValues values = new ContentValues();
        values.put(AlbumsDbHandler.COL_URL, url);
        values.put(AlbumsDbHandler.COL_TITLE, title);
        long id = mDatabase.insertWithOnConflict(AlbumsDbHandler.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        AlbumItem item = new AlbumItem(url, title);
        item.mId = id;
        return item;
    }

    public void deleteAlbum(AlbumItem item) {
        long id = item.mId;
        mDatabase.execSQL(mDbHelper.getAlbumsDbHandler().getDeleteAlbumItemWithIdQuery(id));
    }

    public List<AlbumItem> getAllAlbums() {
        List<AlbumItem> albumItems = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery(mDbHelper.getAlbumsDbHandler().getAllAlbumItemsQuery(), null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            AlbumItem item = cursorToAlbumItem(cursor);
            albumItems.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        return albumItems;
    }

    private AlbumItem cursorToAlbumItem(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(AlbumsDbHandler.COL_ID));
        String url = cursor.getString(cursor.getColumnIndex(AlbumsDbHandler.COL_URL));
        String title = cursor.getString(cursor.getColumnIndex(AlbumsDbHandler.COL_TITLE));
        AlbumItem item = new AlbumItem(url, title);
        item.mId = id;
        return item;
    }
}
