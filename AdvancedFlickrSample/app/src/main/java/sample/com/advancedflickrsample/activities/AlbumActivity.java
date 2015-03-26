package sample.com.advancedflickrsample.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sample.com.advancedflickrsample.R;
import sample.com.advancedflickrsample.adapter.AlbumAdapter;
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
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAlbumListRecyclerView.setLayoutManager(lm);

        ArrayList<ImageItem> items = new ArrayList<>();
        // TODO: remove the resources and add the url and the name.
        items.add(new ImageItem("","", R.drawable.christina));
        items.add(new ImageItem("","", R.drawable.ellie));
        items.add(new ImageItem("","", R.drawable.foster));
        items.add(new ImageItem("","", R.drawable.keane));
        items.add(new ImageItem("","", R.drawable.kodaline));
        items.add(new ImageItem("","", R.drawable.pinkrobots));

        AlbumAdapter adapter = new AlbumAdapter(this, items);
        mAlbumListRecyclerView.setAdapter(adapter);
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
