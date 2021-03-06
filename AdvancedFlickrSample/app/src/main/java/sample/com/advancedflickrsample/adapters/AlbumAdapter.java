package sample.com.advancedflickrsample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import sample.com.advancedflickrsample.R;
import sample.com.advancedflickrsample.entities.AlbumItem;
import sample.com.advancedflickrsample.entities.AlbumViewHolder;

/**
 * Created by hzaied on 3/25/15.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    Context mContext;
    List<AlbumItem> mItemsList;
    AlbumViewHolder.OnViewHolderClickListener mListener;

    public AlbumAdapter(Context context, List<AlbumItem> itemsList,
                        AlbumViewHolder.OnViewHolderClickListener listener) {
        super();
        mContext = context;
        mItemsList = itemsList;
        mListener = listener;
    }

    public void setItemsList(List<AlbumItem> itemsList) {
        this.mItemsList = itemsList;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_grid_item, parent, false);
        return new AlbumViewHolder(view, mListener);
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mItemsList.get(position).mUrl)
                .placeholder(R.drawable.keane)
                .error(R.drawable.foster)
                .into(holder.mAlbumArt);
        holder.mAlbumTitle.setText(mItemsList.get(position).mTitle);
    }
}
