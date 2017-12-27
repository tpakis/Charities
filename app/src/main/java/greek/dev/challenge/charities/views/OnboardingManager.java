package greek.dev.challenge.charities.views;

import android.content.Context;
import android.content.SharedPreferences;

class OnboardingManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "charities-onboarding";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    OnboardingManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
