package sample.com.advancedflickrsample.entities;

import javax.inject.Inject;

/**
 * Created by hzaied on 3/26/15.
 */
public class ImageItem {

    public String mUrl;
    public String mName;
    public int mId;

    public ImageItem(String url, String name, int id) {
        mUrl = url;
        mName = name;
        mId = id;
    }
}
