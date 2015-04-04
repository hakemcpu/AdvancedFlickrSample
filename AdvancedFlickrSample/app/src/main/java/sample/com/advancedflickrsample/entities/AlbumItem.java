package sample.com.advancedflickrsample.entities;

/**
 * Created by hzaied on 3/26/15.
 */
public class AlbumItem {

    public long mId;
    public String mUrl;
    public String mTitle;

    public AlbumItem(String url, String title) {
        mUrl = url;
        mTitle = title;
    }
}
