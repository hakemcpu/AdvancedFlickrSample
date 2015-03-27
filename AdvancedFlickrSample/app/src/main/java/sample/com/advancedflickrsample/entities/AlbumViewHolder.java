package sample.com.advancedflickrsample.entities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sample.com.advancedflickrsample.R;

/**
 * Created by hzaied on 3/26/15.
 */
public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnViewHolderClickListener mListener;
    public @InjectView(R.id.album_art) ImageView mAlbumArt;
    public @InjectView(R.id.album_title) TextView mAlbumTitle;

    public AlbumViewHolder(View itemView, OnViewHolderClickListener listener) {
        super(itemView);

        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(this);

        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null)
            mListener.onViewHolderClicked(this);
    }

    public interface OnViewHolderClickListener {
        void onViewHolderClicked(AlbumViewHolder viewHolder);
    }
}
