package greek.dev.challenge.charities.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import butterknife.BuildConfig;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.WishAdapter;
import greek.dev.challenge.charities.model.Charity;
import greek.dev.challenge.charities.model.Wish;
import greek.dev.challenge.charities.utilities.CharitiesPreferences;

/**
 * Created by nalex on 26/12/2017.
 */

public class ListWishesActivity extends AppCompatActivity {

    ArrayList<Wish> wishes = new ArrayList<>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCharitiesDatabaseReference; //references specific part of the database (wishes here)
    private ChildEventListener mChildEventListener;
    WishAdapter adapter;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishes);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCharitiesDatabaseReference = mFirebaseDatabase.getReference().child("wishes");
        mCharitiesDatabaseReference.keepSynced(true);
        attachDatabaseReadListener();
        CharitiesPreferences preferencesfManager = new CharitiesPreferences(this);
       //η signature του apk
        uid=preferencesfManager.getCharityAp(this);
        RecyclerView rvWishes = findViewById(R.id.rvWishes);
   //θα γίνεται authenticated o χρήστης μέσω email, passowrd
        startAuth(greek.dev.challenge.charities.BuildConfig.USER_APP_ID);
        adapter = new WishAdapter(this, wishes);
        rvWishes.setAdapter(adapter);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rvWishes.setLayoutManager(gridLayoutManager);

        //adding wishes to cloud
        addWishToCloud("Ευτυχισμένο το 2018!", "Μαρία", 2);
        addWishToCloud("Καλά χριστούγεννα σε όλους!", "Γιώργος", 5);
        addWishToCloud("Τα χριστούγεννα πέρασαν!!!!", "Νίκος", 3);
        addWishToCloud("Πρέπει να ποστάρουμε την εφαρμογή ΓΡΗΓΟΡΑΑΑΑΑ", "Θάνος", 2);


    }

    private void addWishToCloud(String wishText, String author, int charityId) {
        //An if statement here is needed to check if the user has made a charity to this charity id
        //Also we could check for valid size of our list and valid id
        Wish wish = new Wish(wishText, author, charityId, System.currentTimeMillis() );
        mCharitiesDatabaseReference.push().setValue(wish);

    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //results from database are deserialized
                    Wish wish = dataSnapshot.getValue(Wish.class);
                    wishes.add(0,wish);
                    adapter.notifyDataSetChanged();
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
    private void startAuth(String uid) {
        Log.v("uid scheme 1",uid);

        Log.v("uid scheme 2",this.uid);
    }
}
