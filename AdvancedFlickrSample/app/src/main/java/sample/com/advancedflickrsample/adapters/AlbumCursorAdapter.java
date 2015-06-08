package sample.com.advancedflickrsample.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sample.com.advancedflickrsample.R;
import sample.com.advancedflickrsample.database.dbHandlers.AlbumsDbHandler;
import sample.com.advancedflickrsample.entities.AlbumViewHolder;

/**
 * Created by hzaied on 3/25/15.
 */
public class AlbumCursorAdapter extends CursorAdapter {
    Context mContext;
    Cursor mCursor;
    AlbumViewHolder.OnViewHolderClickListener mListener;

    public AlbumCursorAdapter(Context context, Cursor cursor,
                              AlbumViewHolder.OnViewHolderClickListener listener) {
        super(context, cursor, false);
        mContext = context;
        mCursor = cursor;
        mListener = listener;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get data from cursor.
        String thumbnail = cursor.getString(cursor.getColumnIndex(AlbumsDbHandler.COL_URL));
        String title = cursor.getString(cursor.getColumnIndex(AlbumsDbHandler.COL_TITLE));

        // Load views
        ImageView albumArt = (ImageView) view.findViewById(R.id.album_art);
        TextView albumTitle = (TextView) view.findViewById(R.id.album_title);

        // Load data
        Picasso.with(mContext)
                .load(thumbnail)
                .placeholder(R.drawable.keane)
                .error(R.drawable.foster)
                .into(albumArt);
        albumTitle.setText(title);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_grid_item, parent, false);
        return view;
    }
}
