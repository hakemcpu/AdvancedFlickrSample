package sample.com.advancedflickrsample.database.dbHandlers;

/**
 * Created by hzaied on 4/3/15.
 */
public class AlbumsDbHandler extends DbHandler {

    public static final String TABLE_NAME = "Albums";
    public static final String COL_ID = "id";
    public static final String COL_URL = "url";
    public static final String COL_TITLE = "title";

    @Override
    public String getCreateQuery() {
        String query = "CREATE TABLE '%s' ('%s' INTEGER UNIQUE, '%s' TEXT PRIMARY KEY, '%s' TEXT );";
        return String.format(query, TABLE_NAME, COL_ID, COL_URL, COL_TITLE);
    }

    @Override
    public String getDropQuery() {
        String query = "DROP TABLE IF EXISTS '%s';";
        return String.format(query, TABLE_NAME);
    }

    public String getAlbumItemFromIdQuery(long albumItemId) {
        String query = "SELECT * FROM '%s' WHERE '%s' = %l";
        return String.format(query, TABLE_NAME, COL_ID, albumItemId);
    }

    public String getDeleteAlbumItemWithIdQuery(long albumItemId) {
        String query = "DELETE FROM '%s' WHERE '%s' = %l";
        return String.format(query, TABLE_NAME, COL_ID, albumItemId);
    }

    public String getAllAlbumItemsQuery() {
        String query = "SELECT * FROM '%s'";
        return String.format(query, TABLE_NAME);
    }
}
