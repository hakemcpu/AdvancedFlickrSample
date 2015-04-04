package sample.com.advancedflickrsample.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sample.com.advancedflickrsample.R;
import sample.com.advancedflickrsample.adapters.AlbumAdapter;
import sample.com.advancedflickrsample.entities.AlbumItem;
import sample.com.advancedflickrsample.entities.AlbumViewHolder;
import sample.com.advancedflickrsample.loaders.ApiRequestLoader;


public class AlbumActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<AlbumItem>>,
        SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.album_list)
    RecyclerView mAlbumListRecyclerView;
    @InjectView(R.id.loading_progress)
    ProgressBar mLoadingProgress;
    @InjectView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    private static final int FLICKR_API_LOADER_ID = 1001;

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
        mLoadingProgress.setVisibility(View.VISIBLE);
        mSwipeToRefresh.setOnRefreshListener(this);

        // Start the loading of data from the webservice in the background.
        getSupportLoaderManager().initLoader(FLICKR_API_LOADER_ID, null, this);
    }

    @Override
    public void onRefresh() {
        // Start the loading of data from the webservice in the background.
        Bundle bundle = new Bundle();
        bundle.putBoolean("force_load", true);
        getSupportLoaderManager().restartLoader(FLICKR_API_LOADER_ID, bundle, this);
    }

    @Override
    public Loader<List<AlbumItem>> onCreateLoader(int id, Bundle args) {
        boolean forceLoad = false;
        if (args != null) forceLoad = args.getBoolean("force_load");
        return new ApiRequestLoader(this, forceLoad);
    }

    @Override
    public void onLoaderReset(Loader<List<AlbumItem>> loader) {
        mAlbumListRecyclerView.setAdapter(null);
        mSwipeToRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadFinished(Loader<List<AlbumItem>> loader, final List<AlbumItem> data) {
        // Handle the initialization of the list with the new data.
        AlbumAdapter adapter = new AlbumAdapter(AlbumActivity.this, data, new AlbumViewHolder.OnViewHolderClickListener() {
            @Override
            public void onViewHolderClicked(AlbumViewHolder viewHolder) {
                String albumArtUrl = data.get(viewHolder.getPosition()).mUrl;
                String albumTitle = data.get(viewHolder.getPosition()).mTitle;

                Intent intent = new Intent(AlbumActivity.this, AlbumDetailActivity.class);
                intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_ART_URL, albumArtUrl);
                intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_TITLE, albumTitle);

                // Showing the details activity with animation in case of lollipop or higher.
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
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAlbumListRecyclerView.setLayoutManager(layoutManager);
        mAlbumListRecyclerView.setAdapter(adapter);
        mLoadingProgress.setVisibility(View.GONE);
        mSwipeToRefresh.setRefreshing(false);
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