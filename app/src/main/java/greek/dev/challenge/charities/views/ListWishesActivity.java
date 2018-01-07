package greek.dev.challenge.charities.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BuildConfig;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private FirebaseAuth mAuth;
    private CharitiesPreferences preferencesfManager;
    private static final String TAG = "EmailPassword";

    @BindView(R.id.send_wish)
    public Button sendButton;

    @BindView(R.id.name)
    public TextView authorOfWish;

    @BindView(R.id.charities_spinner)
    public Spinner spinner;

    @BindView(R.id.wish_text)
    public TextView wishText;

    @BindView(R.id.sliding_layout)
    public SlidingUpPanelLayout mLayout;
    @BindString(R.string.yes)
    public String yesString;

    @BindString(R.string.no)
    public String noString;

    @BindString(R.string.send_Wish_Dialog)
    public String sendWishDialog;

    @BindString(R.string.send_Wish_Dialog_Msg)
    public String sendWishDialogMsg;

    @BindString(R.string.fill_textview)
    public String fillTextView;

    @BindString(R.string.wish_sent)
    public String wishSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishes);
        ButterKnife.bind(this);

        if (FirebaseApp.getApps(getApplicationContext()).isEmpty()) {
            FirebaseApp.initializeApp(getApplicationContext());
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCharitiesDatabaseReference = mFirebaseDatabase.getReference().child("wishes");
        mCharitiesDatabaseReference.keepSynced(true);
        attachDatabaseReadListener();
         preferencesfManager = new CharitiesPreferences(this);
         Log.v("ids list",preferencesfManager.getIds().toString());
       //η signature του apk
        uid=preferencesfManager.getCharityAp(this);
        RecyclerView rvWishes = findViewById(R.id.rvWishes);
   //θα γίνεται authenticated o χρήστης μέσω email, passowrd
        mAuth = FirebaseAuth.getInstance();
        startAuth(greek.dev.challenge.charities.BuildConfig.USER_APP_ID);
        adapter = new WishAdapter(this, wishes);
        rvWishes.setAdapter(adapter);
        setSpinnerList();

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
               // if (newState == PanelState.COLLAPSED){
                //    float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                 //   int dp = Math.round(pixels);
                  //  mLayout.setPanelHeight(dp);
               // }
                Log.i(TAG, "onPanelStateChanged " + newState);

            }
        });
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rvWishes.setLayoutManager(gridLayoutManager);

        //adding wishes to cloud
     //   addWishToCloud("Ευτυχισμένο το 2018!", "Μαρία", 2);
      //  addWishToCloud("Καλά χριστούγεννα σε όλους!", "Γιώργος", 5);
       // addWishToCloud("Τα χριστούγεννα πέρασαν!!!!", "Νίκος", 3);
       // addWishToCloud("Πρέπει να ποστάρουμε την εφαρμογή ΓΡΗΓΟΡΑΑΑΑΑ", "Θάνος", 2);


    }

    private void setSpinnerList(){
       ArrayList<String> namesList = preferencesfManager.getNames();
           ArrayAdapter<String> adapter =
                   new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, namesList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.send_wish)
    public void sendClick(View v) {
        String author = authorOfWish.getText().toString();
        String wish = wishText.getText().toString();
        if (!TextUtils.isEmpty(author) && !TextUtils.isEmpty(wish)) {
            if (canSendWish()) {
                addWishToCloud(wish, author,preferencesfManager.getIdOfName(spinner.getSelectedItem().toString()));
                Toast.makeText(this,wishSent,Toast.LENGTH_SHORT).show();
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                mLayout.setPanelState(PanelState.COLLAPSED);
            }else{
                showAlertToMakeWish();
            }

        }else{
            Toast.makeText(this,fillTextView,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean canSendWish(){
        List<String> tmpList = preferencesfManager.getIds();
        return (!tmpList.isEmpty());
    }

    private void showAlertToMakeWish(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListWishesActivity.this);
        builder.setTitle(sendWishDialog);
        builder.setMessage(sendWishDialogMsg);
        //Yes Button
        builder.setPositiveButton(yesString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(ListWishesActivity.this,CharitiesResultsActivity.class);
                startActivity(i);
                dialog.dismiss();
                finish();
            }
        });
        //No Button
        builder.setNegativeButton(noString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addWishToCloud(String wishText, String author, int charityId) {
        //An if statement here is needed to check if the user has made a charity to this charity id
        //Also we could check for valid size of our list and valid id
        Wish wish = new Wish(wishText, author, charityId, System.currentTimeMillis() );
        mCharitiesDatabaseReference.push().setValue(wish);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
     //   updateUI(currentUser);
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
        // [END sign_in_with_email]
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
        signIn("test@greekcharities.com","353535");
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
