package greek.dev.challenge.charities.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import greek.dev.challenge.charities.utilities.CharitiesPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Checking for first time launch
        CharitiesPreferences onboardingManager = new CharitiesPreferences(this);
        if (!onboardingManager.isFirstTimeLaunch()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, OnboardingActivity.class));
        }
        finish();
    }
}
