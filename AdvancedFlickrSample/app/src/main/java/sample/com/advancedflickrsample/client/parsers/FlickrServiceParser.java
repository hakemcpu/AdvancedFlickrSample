package sample.com.advancedflickrsample.client.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sample.com.advancedflickrsample.database.DataSource.AlbumsDataSource;
import sample.com.advancedflickrsample.entities.AlbumItem;

/**
 * Created by hzaied on 3/27/15.
 */
public class FlickrServiceParser extends ApiParser<List<AlbumItem>> {
    AlbumsDataSource mDataSource;

    public FlickrServiceParser(AlbumsDataSource dataSource) {
        this.mDataSource = dataSource;
    }

    @Override
    public List<AlbumItem> parse(String data) {
        try {
            // Parse the response from the flickr API.
            JSONObject responseJSON = new JSONObject(data);
            JSONObject photos = responseJSON.getJSONObject("photos");
            JSONArray photosArray = photos.getJSONArray("photo");

            // List that contains the result of the data.
            List<AlbumItem> result = new ArrayList<>();
            for (int i = 0; i < photosArray.length(); i++) {
                try {
                    // Parse the photo and add it to the list of photos.
                    AlbumItem imageItem = parsePhoto(photosArray.getJSONObject(i));
                    result.add(imageItem);
                } catch (Exception e) {
//                    Log.e(Constants.TAG, "Failed in parsing photo");
                }
            }

            return result;
        } catch (Exception e) {
//            Log.e(Constants.TAG, "Error in parsing the response and saving it with Exception " + e.getMessage());
            return null;
        }
    }

    /**
     * Parse a single photo from the json object.
     *
     * @param photoObject
     * @return
     * @throws JSONException
     */
    private AlbumItem parsePhoto(JSONObject photoObject) throws JSONException {
        // A Photo is generated from multiple parts as shown in the below
        // example:
        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        String photoURLFormat = "https://farm%s.staticflickr.com/%s/%s_%s.jpg";

        String farmId = photoObject.getString("farm");
        String serverId = photoObject.getString("server");
        String photoId = photoObject.getString("id");
        String secret = photoObject.getString("secret");
        String title = photoObject.getString("title");

        String photoUrl = String.format(photoURLFormat, farmId, serverId, photoId, secret);
        mDataSource.createAlbum(photoUrl, title, Calendar.getInstance().getTimeInMillis());
        return new AlbumItem(photoUrl, title);
    }
}
