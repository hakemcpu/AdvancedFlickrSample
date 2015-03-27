package sample.com.advancedflickrsample.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import sample.com.advancedflickrsample.R;
import sample.com.advancedflickrsample.adapter.AlbumAdapter;
import sample.com.advancedflickrsample.client.FlickrService;
import sample.com.advancedflickrsample.client.JsonConverter;
import sample.com.advancedflickrsample.client.parsers.FlickrServiceParser;
import sample.com.advancedflickrsample.entities.AlbumViewHolder;
import sample.com.advancedflickrsample.entities.ImageItem;


public class AlbumActivity extends ActionBarActivity {

    @InjectView(R.id.album_list)
    RecyclerView mAlbumListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        ButterKnife.inject(this);
        populateList();
    }

    private void populateList() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAlbumListRecyclerView.setLayoutManager(layoutManager);


        final ArrayList<ImageItem> items = new ArrayList<>();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                AlbumAdapter adapter = new AlbumAdapter(AlbumActivity.this, items, new AlbumViewHolder.OnViewHolderClickListener() {
                    @Override
                    public void onViewHolderClicked(AlbumViewHolder viewHolder) {
                        String albumArtUrl = items.get(viewHolder.getPosition()).mUrl;
                        String albumTitle = items.get(viewHolder.getPosition()).mTitle;

                        Intent intent = new Intent(AlbumActivity.this, AlbumDetailActivity.class);
                        intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_ART_URL, albumArtUrl);
                        intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_TITLE, albumTitle);

                        // Showing the new screen with animation in case of lollipop or higher.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AlbumActivity.this,
                                    Pair.create((View) viewHolder.mAlbumArt, getString(R.string.album_art_string)),
                                    Pair.create((View) viewHolder.mAlbumTitle, getString(R.string.album_title_string)));
                            startActivity(intent, options.toBundle());
                        } else {
                            startActivity(intent);
                        }
                    }
                });
                mAlbumListRecyclerView.setAdapter(adapter);
                return false;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("https://api.flickr.com")
                        .setConverter(new JsonConverter(new FlickrServiceParser()))
                        .build();

                // Call the flickr explor webservice.
                FlickrService flickrService = restAdapter.create(FlickrService.class);
                items.addAll(flickrService.getFlickrExplorList("flickr.interestingness.getList", "json"));

                handler.sendEmptyMessage(0);
            }
        }).start();

        // TODO: remove the resources and add the url and the name.
//        items.add(new ImageItem("", "", R.drawable.christina));
//        items.add(new ImageItem("", "", R.drawable.ellie));
//        items.add(new ImageItem("", "", R.drawable.foster));
//        items.add(new ImageItem("", "", R.drawable.keane));
//        items.add(new ImageItem("", "", R.drawable.kodaline));
//        items.add(new ImageItem("", "", R.drawable.pinkrobots));
//
//        AlbumAdapter adapter = new AlbumAdapter(this, items, new AlbumViewHolder.OnViewHolderClickListener() {
//            @Override
//            public void onViewHolderClicked(AlbumViewHolder viewHolder) {
//                int albumArtResId = items.get(viewHolder.getPosition() % items.size()).mId;
//                Intent intent = new Intent(AlbumActivity.this, AlbumDetailActivity.class);
//                intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_ART_URL, albumArtResId);
//
//                // Showing the new screen with animation in case of lollipop or higher.
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AlbumActivity.this,
//                            Pair.create((View) viewHolder.mAlbumArt, getString(R.string.album_art_string)));
//                    startActivity(intent, options.toBundle());
//                } else {
//                    startActivity(intent);
//                }
//            }
//        });
//        mAlbumListRecyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
