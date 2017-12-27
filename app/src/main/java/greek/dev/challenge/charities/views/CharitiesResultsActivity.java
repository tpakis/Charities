package greek.dev.challenge.charities.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.ResultsAdapter;
import greek.dev.challenge.charities.model.Charity;

public class CharitiesResultsActivity extends AppCompatActivity implements ResultsAdapter.CharitiesResultsAdapterOnClickHandler, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.rv_results)
    public RecyclerView rv_charities;

    @BindView(R.id.tv_error_message)
    public TextView tv_error_message;

    @BindView(R.id.pb_loading_indicator)
    public ProgressBar pb_loading_indicator;

    @BindView(R.id.todo_list_empty_view)
    public LinearLayout emptyView;

    private ResultsAdapter mCharitiesAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCharitiesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCharitiesPhotosStorageReference;
    private MainViewModel viewModel;

    static boolean calledFirebaseAlready = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_results);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rv_charities.setLayoutManager(layoutManager);
        rv_charities.setHasFixedSize(true);

        mCharitiesAdapter = new ResultsAdapter(this);

        rv_charities.setAdapter(mCharitiesAdapter);

        pb_loading_indicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        //get reference to specific part of database - messages with mMessagesDatabaseReference

        if (!calledFirebaseAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledFirebaseAlready = true;
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        mCharitiesDatabaseReference = mFirebaseDatabase.getReference().child("charities");
        mCharitiesDatabaseReference.keepSynced(true);
        // mCharitiesPhotosStorageReference = mFirebaseStorage.getReference().child("charities_photos");

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getCharitiesList().observe(CharitiesResultsActivity.this, new Observer<ArrayList<Charity>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Charity> charitiesList) {
                emptyView.setVisibility(View.GONE);
                mCharitiesAdapter.setSeriesResults(charitiesList);
                Log.v("main", charitiesList.get(charitiesList.size()-1).toString());
                Log.v("main", String.valueOf(charitiesList.size()));
                runLayoutAnimation(rv_charities);
            }
        });


     /*   // TODO THE CODE BELLOW IS FOR TESTING. has to be removed to implement it using the real data.
        // --------------------------------------------------------
        Charity test1 = new Charity(1, "Charity Name 1", "lalala1", "5", "test", "test", "10", "10", "lala", "test");
        Charity test2 = new Charity(2, "Charity Name 2", "lalala2", "6", "test2", "test2", "102", "102", "lal2a", "test2");

        ArrayList<Charity> testing = new ArrayList<Charity>();
        testing.add(test1);
        testing.add(test2);
        // ----------------------------------------------------------
*/

    }
    @Override
    protected void onResume() {
        super.onResume();
        // an exei ksanakatevasei ta dedomena den xreiazetai na ta ksanatraviksoume
        if (!viewModel.fetched){
            attachDatabaseReadListener();
        }
    }
    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //here we get the results from the firebase db
                    Charity charity = dataSnapshot.getValue(Charity.class);
                    viewModel.addCharity(charity);
                    viewModel.fetched = true;
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mCharitiesDatabaseReference.addChildEventListener(mChildEventListener);

        }
    }
    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mCharitiesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        onSignedOutCleanup();
    }
    //not signed out now, but a cleanup is required onPause, so not to get duplicate EventListeners
    private void onSignedOutCleanup(){
        detachDatabaseReadListener();

    }

    private void showCharitiesList() {
        tv_error_message.setVisibility(View.INVISIBLE);
        rv_charities.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rv_charities.setVisibility(View.INVISIBLE);
        tv_error_message.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Charity selectedCharity) {
        Context context = this;
        Intent intent = new Intent(context, CharityDetails.class);
        intent.putExtra("charity",selectedCharity);
        //intent.putExtra(Intent.EXTRA_TEXT, ""+selectedCharity.getId());
        startActivity(intent);
    }
    // Animation RecyclerView
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.rv_layout_animation);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
    public static Map getDrawablesMap(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("actionaid", R.drawable.actionaid);
        map.put("antikarkiniki", R.drawable.antikarkiniki);
        map.put("anima", R.drawable.anima);
        map.put("edke", R.drawable.edke);
        map.put("elepap", R.drawable.elepap);
        map.put("elpida", R.drawable.elpida);
        map.put("grammis_sos", R.drawable.grammis_sos);
        map.put("i_pisti", R.drawable.i_pisti);
        map.put("iagkalia", R.drawable.iagkalia);

        map.put("kibotos", R.drawable.kibotos);
        map.put("makeawish", R.drawable.makeawish);
        map.put("theofilos", R.drawable.theofilos);
        map.put("unicef", R.drawable.unicef);
        map.put("xamogelo_paidiou", R.drawable.xamogelo_paidiou);
        map.put("xwria_sos", R.drawable.xwria_sos);
        return map;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i = new Intent(CharitiesResultsActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_donate) {
            Intent i = new Intent(CharitiesResultsActivity.this, CharitiesResultsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_add_donation) {
            Intent i = new Intent(CharitiesResultsActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_make_wish) {
            Intent i = new Intent(CharitiesResultsActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_info) {
            Intent i = new Intent(CharitiesResultsActivity.this, MainActivity.class);
            startActivity(i);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
