package greek.dev.challenge.charities.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.ImageAdapter;
import greek.dev.challenge.charities.model.Charity;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCharitiesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mCharitiesPhotosStorageReference;
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if (position == 0) {
                    Intent myIntent = new Intent(v.getContext(), CharitiesResultsActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });
        //get reference to specific part of database - messages with mMessagesDatabaseReference
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        mCharitiesDatabaseReference = mFirebaseDatabase.getReference().child("charities");
       // mCharitiesPhotosStorageReference = mFirebaseStorage.getReference().child("charities_photos");

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getCharitiesList().observe(MainActivity.this, new Observer<List<Charity>>() {
            @Override
            public void onChanged(@Nullable List<Charity> charitiesList) {
                Log.v("main", charitiesList.get(charitiesList.size()-1).toString());
                Log.v("main", String.valueOf(charitiesList.size()));
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }
    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //here we get the results from the firebase db
                    Charity charity = dataSnapshot.getValue(Charity.class);
                    viewModel.addCharity(charity);
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
}
