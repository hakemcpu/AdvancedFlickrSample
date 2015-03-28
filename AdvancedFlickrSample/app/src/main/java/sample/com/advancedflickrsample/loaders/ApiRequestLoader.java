package sample.com.advancedflickrsample.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import sample.com.advancedflickrsample.client.FlickrService;
import sample.com.advancedflickrsample.client.JsonConverter;
import sample.com.advancedflickrsample.client.parsers.FlickrServiceParser;
import sample.com.advancedflickrsample.entities.ImageItem;

/**
 * Created by hzaied on 3/28/15.
 */
public class ApiRequestLoader extends AbstractLoader<List<ImageItem>> {

    public ApiRequestLoader(Context context) {
        super(context);
    }

    @Override
    public List<ImageItem> loadInBackground() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.flickr.com")
                .setConverter(new JsonConverter(new FlickrServiceParser()))
                .build();

        // Call the flickr explor webservice.
        FlickrService flickrService = restAdapter.create(FlickrService.class);
        return flickrService.getFlickrExplorList("flickr.interestingness.getList", "json");
    }
}
