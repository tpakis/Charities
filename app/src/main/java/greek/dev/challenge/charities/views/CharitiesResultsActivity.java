package greek.dev.challenge.charities.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.ResultsAdapter;
import greek.dev.challenge.charities.model.Charity;

public class CharitiesResultsActivity extends AppCompatActivity implements ResultsAdapter.CharitiesResultsAdapterOnClickHandler {

    @BindView(R.id.rv_results)
    public RecyclerView rv_charities;

    @BindView(R.id.tv_error_message)
    public TextView tv_error_message;

    @BindView(R.id.pb_loading_indicator)
    public ProgressBar pb_loading_indicator;

    private ResultsAdapter mCharitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charities_results);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rv_charities.setLayoutManager(layoutManager);
        rv_charities.setHasFixedSize(true);

        mCharitiesAdapter = new ResultsAdapter(this);

        rv_charities.setAdapter(mCharitiesAdapter);

        pb_loading_indicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // TODO THE CODE BELLOW IS FOR TESTING. has to be removed to implement it using the real data.
        // --------------------------------------------------------
        Charity test1 = new Charity(1, "Charity Name 1", "lalala1", 5, "test", "test", 10, "10", "lala", "test");
        Charity test2 = new Charity(2, "Charity Name 2", "lalala2", 6, "test2", "test2", 102, "102", "lal2a", "test2");

        ArrayList<Charity> testing = new ArrayList<Charity>();
        testing.add(test1);
        testing.add(test2);
        // ----------------------------------------------------------

        mCharitiesAdapter.setSeriesResults(testing);
    }


    private void showCharitiesList() {
        tv_error_message.setVisibility(View.INVISIBLE);
        rv_charities.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rv_charities.setVisibility(View.INVISIBLE);
        tv_error_message.setVisibility(View.VISIBLE);
    }


    // we pass the id of the charity selected to the detail activity in order to be able to get the needed info later
    @Override
    public void onClick(Charity selectedCharity) {
        Context context = this;
        Intent intent = new Intent(context, CharityDetails.class);
        intent.putExtra(Intent.EXTRA_TEXT, ""+selectedCharity.getId());
        startActivity(intent);
    }
}
