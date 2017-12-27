package greek.dev.challenge.charities.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gridview)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gridView.setAdapter(new ImageAdapter(this));
    }

    @OnItemClick(R.id.gridview)
    public void textShareSocialClick(View view, int position, long id){
        switch (position){
            case 0:
                Intent myIntent = new Intent(view.getContext(), CharitiesResultsActivity.class);
                startActivityForResult(myIntent, 0);
                break;
            case 1:
                openGoogleform();
                break;
            case 2:
                Intent startWishList = new Intent(view.getContext(), ListWishesActivity.class);
                startActivityForResult(startWishList, 0);
                break;
            case 3:
                break;
        }
    }
    @OnClick({R.id.textViewShare,R.id.textViewSocial})
    public void textShareSocialClick(View view) {
        shareApp(this);
    }

    private static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        final String appName = context.getString(R.string.app_name);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBodyText = "https://play.google.com/store/apps/details?id=" +
                appPackageName;
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        context.startActivity(Intent.createChooser(shareIntent, "Μοιραστείτε την εφαρμογή σε:"));
    }
    private void openGoogleform(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        builder.setTitle("Προσθήκη Ιδρύματος");


        builder.setMessage(getResources().getString(R.string.open_dialog));


        //Yes Button
        builder.setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://goo.gl/forms/wtJQeDD4VclJhfjk2"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        });

        //No Button
        builder.setNegativeButton("Όχι", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

/*
        //Cancel Button
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Cancel button Clicked",Toast.LENGTH_LONG).show();
                Log.i("Code2care ","Cancel button Clicked!");
                dialog.dismiss();
            }
        });*/


        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
