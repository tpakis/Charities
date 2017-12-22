package greek.dev.challenge.charities.views;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.model.Charity;

public class CharityDetails extends AppCompatActivity {

    @BindView(R.id.detail_charity_icon)
    public ImageView iv_charity_icon;

    @BindView(R.id.detail_charity_default_icon)
    public ImageView iv_charity_default_icon;

    @BindView(R.id.detail_charity_name)
    public TextView tv_charity_name;

    @BindView(R.id.detail_desc_text)
    public TextView tv_charity_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_details);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intentStartedThisActivity = getIntent();
        Charity selectedCharity = intentStartedThisActivity.getExtras().getParcelable("charity");
        setData(selectedCharity);
        /*if(intentStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            int selectedCharityId = Integer.parseInt(intentStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT));
            setData(selectedCharityId);
        }*/

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

                tv_charity_name.setText(selectedCharity.getName());
                tv_charity_desc.setText(selectedCharity.getDescription());
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

}
