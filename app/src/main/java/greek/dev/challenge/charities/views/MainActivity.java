package greek.dev.challenge.charities.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    @BindString(R.string.add_institute)
    public String addInstitute;
    @BindString(R.string.yes)
    public String yesString;
    @BindString(R.string.no)
    public String noString;
    @BindString(R.string.new_form_link)
    public String googleFormLink;
    @BindString(R.string.open_dialog)
    public String openDialog;
    @BindView(R.id.gridview)
    GridView gridView;

    private static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        final String appName = context.getString(R.string.app_name);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBodyText = context.getString(R.string.google_play_prefix) +
                appPackageName;
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_text)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gridView.setAdapter(new ImageAdapter(this));
    }

    @OnItemClick(R.id.gridview)
    public void textShareSocialClick(View view, int position, long id) {
        switch (position) {
            case 0:
                Intent myIntent = new Intent(view.getContext(), CharitiesResultsActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(myIntent, 0);
                finish();
                break;
            case 1:
                openGoogleform();
                break;
            case 2:
                Intent startWishList = new Intent(view.getContext(), ListWishesActivity.class);
                startActivityForResult(startWishList, 0);
                break;
            case 3:
                Intent myInfo = new Intent(view.getContext(), InfoActivity.class);
                startActivity(myInfo);
                break;
        }
    }

    @OnClick({R.id.textViewShare, R.id.textViewSocial})
    public void textShareSocialClick(View view) {
        shareApp(this);
    }

    private void openGoogleform() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(addInstitute);
        builder.setMessage(openDialog);
        //Yes Button
        builder.setPositiveButton(yesString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(googleFormLink));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                dialog.dismiss();
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

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
