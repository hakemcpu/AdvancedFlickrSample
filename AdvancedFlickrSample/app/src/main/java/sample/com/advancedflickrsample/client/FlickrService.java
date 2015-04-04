package sample.com.advancedflickrsample.client;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import sample.com.advancedflickrsample.entities.AlbumItem;

/**
 * Created by hzaied on 3/27/15.
 */
public interface FlickrService {

    @GET("/services/rest?api_key=5f412b6f21aa9b6978c979c1ca806375&nojsoncallback=1")
    List<AlbumItem> getFlickrExplorList(@Query("method") String method, @Query("format") String format);
}
