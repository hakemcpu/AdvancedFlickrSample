package sample.com.advancedflickrsample.loaders;

import android.content.Context;
import android.database.Cursor;

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
public class ApiRequestCursorLoader extends AbstractLoader<Cursor> {
    private boolean mForceUpdate = false;

    public ApiRequestCursorLoader(Context context, boolean forceUpdate) {
        super(context);
        mForceUpdate = forceUpdate;
    }

    @Override
    public Cursor loadInBackground() {
        AlbumsDataSource dataSource = new AlbumsDataSource(getContext());
        dataSource.open();

        if (mForceUpdate) {
            dataSource.deleteAll();
            callApi(dataSource);
        }

        Cursor cursor = dataSource.getAllAlbumsAsCursor();
        dataSource.close();
        return cursor;
    }

    private void callApi(AlbumsDataSource dataSource) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.flickr.com")
                .setConverter(new JsonConverter(new FlickrServiceParser(dataSource)))
                .build();

        // Call the flickr explor webservice.
        FlickrService flickrService = restAdapter.create(FlickrService.class);
        flickrService.getFlickrExplorList("flickr.interestingness.getList", "json");
    }
}
