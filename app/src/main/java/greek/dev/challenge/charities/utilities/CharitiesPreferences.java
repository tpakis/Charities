package greek.dev.challenge.charities.utilities;

/*
 * Created by geoDev on 28/12/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("UnusedReturnValue")
public class CharitiesPreferences {

    private static final String PREF_NAME = "charities-prefs";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private SharedPreferences reader;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public CharitiesPreferences(Context context) {
        this.reader = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = this.reader.edit();
    }

    public boolean saveId(int id){
        if(!isIdExists(id)) {
            Set<String> set = this.reader.getStringSet("donatedIds", new HashSet<String>());

            set.add(String.valueOf(id));

            this.editor.putStringSet("donatedIds", set);
            this.editor.apply();

            return true;
        }

        return false;
    }

    public boolean isIdExists(int id){
        Set<String> set = this.reader.getStringSet("donatedIds", new HashSet<String>());

        return set.contains(String.valueOf(id));
    }

    public ArrayList<String> getIds(){
        Set<String> set = this.reader.getStringSet("donatedIds", new HashSet<String>());
        ArrayList<String> list = new ArrayList<String>(set);
         return list;
    }

    // Onboarding
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public String getCharityAp(Context context) {
        try {
            Signature sig = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures[0];
            return sig.toCharsString();
        }catch (PackageManager.NameNotFoundException e){
            Log.v("not found",e.toString());
            return "";
        }
    }

    public boolean isFirstTimeLaunch() {
        return reader.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
