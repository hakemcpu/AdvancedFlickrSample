package sample.com.advancedflickrsample.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import sample.com.advancedflickrsample.R;
import sample.com.advancedflickrsample.entities.AlbumViewHolder;
import sample.com.advancedflickrsample.entities.ImageItem;

/**
 * Created by hzaied on 3/25/15.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

    ArrayList<ImageItem> mItemsList;
    Context mContext;

    public AlbumAdapter(Context context, ArrayList<ImageItem> itemsList) {
        super();
        mContext = context;
        mItemsList = itemsList;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_grid_item, parent, false);
        return new AlbumViewHolder(view, new AlbumViewHolder.OnViewHolderClickListener() {
            @Override
            public void onViewHolderClicked(AlbumViewHolder viewHolder) {
                Toast.makeText(mContext, "Clicked on item #" + viewHolder.getPosition(), Toast.LENGTH_SHORT).show();
//                int albumArtResId = mItemsList.get(viewHolder.getPosition()%mItemsList.size()).mId;
//                Intent intent = new Intent(mContext, AlbumDetailActivity.class);
//                intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_ART_RESID, albumArtResId);
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemsList.size() * 4;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.mAlbumArt.setImageResource(mItemsList.get(position%mItemsList.size()).mId);
    }
}
