package sample.com.advancedflickrsample.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sample.com.advancedflickrsample.R;
import sample.com.advancedflickrsample.adapters.AlbumCursorAdapter;
import sample.com.advancedflickrsample.database.dbHandlers.AlbumsDbHandler;
import sample.com.advancedflickrsample.loaders.ApiRequestCursorLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumListViewActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    @InjectView(R.id.album_list)
    GridView mAlbumGridView;
    @InjectView(R.id.loading_progress)
    ProgressBar mLoadingProgress;
    @InjectView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwipeToRefresh;

    private static final int FLICKR_API_LOADER_ID = 1001;

    public AlbumListViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list_view, container, false);
        ButterKnife.inject(this, view);
        populateList();
        return view;
    }

    private void populateList() {
        mLoadingProgress.setVisibility(View.VISIBLE);
        mSwipeToRefresh.setOnRefreshListener(this);

        // Start the loading of data from the webservice in the background.
        getActivity().getSupportLoaderManager().initLoader(FLICKR_API_LOADER_ID, null, this);
    }

    @Override
    public void onRefresh() {
        // Start the loading of data from the webservice in the background.
        Bundle bundle = new Bundle();
        bundle.putBoolean("force_load", true);
        getActivity().getSupportLoaderManager().restartLoader(FLICKR_API_LOADER_ID, bundle, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        boolean forceLoad = false;
        if (args != null) forceLoad = args.getBoolean("force_load");
        return new ApiRequestCursorLoader(getActivity(), forceLoad);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAlbumGridView.setAdapter(null);
        mSwipeToRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        // Incase of empty list update.
        if (data == null || data.isClosed()) {
            mLoadingProgress.setVisibility(View.VISIBLE);
            mSwipeToRefresh.setRefreshing(true);

            Bundle bundle = new Bundle();
            bundle.putBoolean("force_load", true);
            getActivity().getSupportLoaderManager().restartLoader(FLICKR_API_LOADER_ID, bundle, this);
            return;
        }

        // Handle the initialization of the list with the new data.
        AlbumCursorAdapter adapter = new AlbumCursorAdapter(getActivity(), data, null);
        mAlbumGridView.setAdapter(adapter);
        mAlbumGridView.setOnItemClickListener(this);
        mLoadingProgress.setVisibility(View.GONE);
        mSwipeToRefresh.setRefreshing(false);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);

        String albumArtUrl = cursor.getString(cursor.getColumnIndex(AlbumsDbHandler.COL_URL));
        String albumTitle = cursor.getString(cursor.getColumnIndex(AlbumsDbHandler.COL_TITLE));

        Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
        intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_ART_URL, albumArtUrl);
        intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_TITLE, albumTitle);

        startActivity(intent);
    }
}
