package sample.com.advancedflickrsample.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import sample.com.advancedflickrsample.client.FlickrService;
import sample.com.advancedflickrsample.client.JsonConverter;
import sample.com.advancedflickrsample.client.parsers.FlickrServiceParser;
import sample.com.advancedflickrsample.database.DataSource.AlbumsDataSource;
import sample.com.advancedflickrsample.entities.AlbumItem;

/**
 * Created by hzaied on 3/28/15.
 */
public class ApiRequestLoader extends AbstractLoader<List<AlbumItem>> {
    private boolean mForceUpdate = false;

    public ApiRequestLoader(Context context, boolean forceUpdate) {
        super(context);
        mForceUpdate = forceUpdate;
    }

    @Override
    public List<AlbumItem> loadInBackground() {
        AlbumsDataSource dataSource = new AlbumsDataSource(getContext());
        dataSource.open();

        if (mForceUpdate) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.flickr.com")
                    .setConverter(new JsonConverter(new FlickrServiceParser(dataSource)))
                    .build();

            // Call the flickr explor webservice.
            FlickrService flickrService = restAdapter.create(FlickrService.class);
            flickrService.getFlickrExplorList("flickr.interestingness.getList", "json");
        }
        List<AlbumItem> items = dataSource.getAllAlbums();
        dataSource.close();
        return items;
    }
}
