package sample.com.advancedflickrsample.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sample.com.advancedflickrsample.R;

public class AlbumDetailActivity extends ActionBarActivity {
    public static final String EXTRA_ALBUM_ART_URL = "EXTRA_ALBUM_ART_URL";
    public static final String EXTRA_ALBUM_TITLE = "EXTRA_ALBUM_TITLE";

    @InjectView(R.id.album_art) ImageView mAlbumArt;
    @InjectView(R.id.title) TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        ButterKnife.inject(this);
        populate();
    }

    private void populate() {
        String albumArtUrl = getIntent().getStringExtra(EXTRA_ALBUM_ART_URL);
        Picasso.with(this)
                .load(albumArtUrl)
                .placeholder(R.drawable.foster)
                .error(R.drawable.keane)
                .into(mAlbumArt);

        String title = getIntent().getStringExtra(EXTRA_ALBUM_TITLE);
        mTitle.setText(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_detail, menu);
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
