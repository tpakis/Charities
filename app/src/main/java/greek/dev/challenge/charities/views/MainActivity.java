package greek.dev.challenge.charities.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.support.design.widget.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Optional;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.adapters.ImageAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.gridview)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        gridView.setAdapter(new ImageAdapter(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Nullable
    @OnItemClick(R.id.gridview)
    public void textShareSocialClick(View view, int position, long id) {
        switch (position) {
            case 0:
                Intent myIntent = new Intent(view.getContext(), CharitiesResultsActivity.class);
                startActivityForResult(myIntent, 0);
                break;
            case 1:
                openGoogleform();
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Nullable
    @OnClick({R.id.textViewShare, R.id.textViewSocial})
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

    private void openGoogleform() {
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_donate) {
            Intent i = new Intent(MainActivity.this, CharitiesResultsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_add_donation) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_make_wish) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_info) {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
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
