package greek.dev.challenge.charities.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.model.Charity;
import greek.dev.challenge.charities.utilities.CallAndSms;
import greek.dev.challenge.charities.utilities.CharitiesPreferences;

public class CharityDetails extends AppCompatActivity {

    private static final int REQUEST_CODE = 999;

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
    private CharitiesPreferences charitiesPreferences;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            charitiesPreferences.saveCharity(selectedCharity.getName(),selectedCharity.getId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_details);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.charitiesPreferences = new CharitiesPreferences(this);

        Intent intentStartedThisActivity = getIntent();
        selectedCharity = intentStartedThisActivity.getExtras().getParcelable("charity");
        setData(selectedCharity);
    }

    @OnClick(R.id.detail_button_call)
    public void callButton(View view){
        Intent i = CallAndSms.call(this,selectedCharity.getTelephone());
        if (i != null){
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    @OnClick(R.id.detail_button_sms)
    public void smsButton(View view){
        Intent i = CallAndSms.sms(this,selectedCharity.getSms(),selectedCharity.getSmstext());
        if (i != null){
            startActivityForResult(i, REQUEST_CODE);
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
    }

}
