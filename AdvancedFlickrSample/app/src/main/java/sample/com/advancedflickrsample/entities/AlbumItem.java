package sample.com.advancedflickrsample.entities;

/**
 * Data entity for the Album item.
 */
public class AlbumItem {

    public long mId;
    public String mUrl;
    public String mTitle;
    public long mTime;

    public AlbumItem(String url, String title) {
        mUrl = url;
        mTitle = title;
    }
}
