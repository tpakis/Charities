package greek.dev.challenge.charities.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;


import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.ImageAdapter;

public class MainActivity extends AppCompatActivity {
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

    }
}
