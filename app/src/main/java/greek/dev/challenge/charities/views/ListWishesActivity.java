package greek.dev.challenge.charities.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.WishAdapter;
import greek.dev.challenge.charities.model.Wish;

/**
 * Created by nalex on 26/12/2017.
 */

public class ListWishesActivity extends AppCompatActivity {

    ArrayList<Wish> wishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishes);

        RecyclerView rvWishes = (RecyclerView) findViewById(R.id.rvWishes);

        wishes = Wish.createWishList(100);

        WishAdapter adapter = new WishAdapter(this, wishes);

        rvWishes.setAdapter(adapter);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rvWishes.setLayoutManager(gridLayoutManager);
    }

}
