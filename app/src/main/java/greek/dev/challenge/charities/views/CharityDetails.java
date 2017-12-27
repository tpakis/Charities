package greek.dev.challenge.charities.views;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.model.Charity;
import greek.dev.challenge.charities.utilities.CallAndSms;

public class CharityDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.detail_charity_icon)
    public CircleImageView iv_charity_icon;

    @BindView(R.id.detail_charity_default_icon)
    public IconicsImageView iv_charity_default_icon;

    @BindView(R.id.detail_charity_name)
    public TextView tv_charity_name;

    @BindView(R.id.detail_desc_text)
    public TextView tv_charity_desc;

    @BindView(R.id.detail_button_call)
    public Button makeCall;

    @BindView(R.id.detail_button_sms)
    public Button sendSms;

    @BindView(R.id.detail_call_cost)
    public TextView tv_call_cost;

    @BindView(R.id.detail_sms_cost)
    public TextView tv_sms_cost;

    Charity selectedCharity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details);
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


        Intent intentStartedThisActivity = getIntent();
        selectedCharity = intentStartedThisActivity.getExtras().getParcelable("charity");
        setData(selectedCharity);
    }

    @OnClick(R.id.detail_button_call)
    public void callButton(View view){
        Intent i = CallAndSms.call(this,selectedCharity.getTelephone());
        if (i != null){
            startActivity(i);
        }
    }

    @OnClick(R.id.detail_button_sms)
    public void smsButton(View view){
        Intent i = CallAndSms.sms(this,selectedCharity.getSms(),selectedCharity.getSmstext());
        if (i != null){
            startActivity(i);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData(Charity selectedCharity){

                if(selectedCharity.getIconlink()!="")
                {
                    iv_charity_icon.setImageResource(selectedCharity.getDrawableIconPosition());
                    iv_charity_default_icon.setVisibility(View.GONE);
                }
                tv_charity_name.setText(selectedCharity.getName());
                tv_charity_desc.setText(selectedCharity.getDescription());
                if (selectedCharity.getSms().equals("0")){
                    sendSms.setEnabled(false);
                    tv_sms_cost.setText("");
                }else{
                    tv_sms_cost.setText(selectedCharity.getSmscost());
                }
                if (selectedCharity.getTelephone().equals("0")){
                    makeCall.setEnabled(false);
                    tv_call_cost.setText("");
                }else{
                    tv_call_cost.setText(selectedCharity.getTelephonecost());
                }

        // ------------------------------------------------

        //same idea like in adapter

                 /* NOTE. if the charity's image is available, then we should use:
            holder.iv_charity_default_icon.setVisibility(View.INVISIBLE);
            holder.iv_charity_icon.setImageResource(); (or a similar method to set the source)
            but if the charity's image in not available, then we should use:
            holder.iv_charity_default_icon.setVisibility(View.VISIBLE);
            holder.iv_charity_icon.setImageResource(R.drawable.no_charity_icon_bg);  */

        // has to be implemented the getIconlink provides link not image
        // holder.iv_charity_icon.setImageResource(charityObject.getIconLink());
        // The user can't send data to the firebase db yet, so it can't be implemented
        // holder.rb_rating.setNumStars(sampleDataObject.getStars());


    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i = new Intent(CharityDetails.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_donate) {
            Intent i = new Intent(CharityDetails.this, CharitiesResultsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_add_donation) {
            Intent i = new Intent(CharityDetails.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_make_wish) {
            Intent i = new Intent(CharityDetails.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_info) {
            Intent i = new Intent(CharityDetails.this, MainActivity.class);
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
