package greek.dev.challenge.charities.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private OnboardingManager onboardingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch
        onboardingManager = new OnboardingManager(this);
        if (!onboardingManager.isFirstTimeLaunch()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, OnboardingActivity.class));
        }
        finish();
    }
}
