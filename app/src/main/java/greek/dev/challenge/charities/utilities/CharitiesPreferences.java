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

    public boolean saveCharity(String name, int id) {
        saveId(id);
        saveName(name, id);
        return true;
    }

    public boolean saveId(int id) {
        if (!isIdExists(id)) {
            Set<String> set = this.reader.getStringSet("donatedIds", new HashSet<String>());

            set.add(String.valueOf(id));

            this.editor.putStringSet("donatedIds", set);
            this.editor.putInt(String.valueOf(id), 1);
            this.editor.apply();

            return true;
        } else {
            int donationsNumber = 0;
            try {
                this.reader.getInt(String.valueOf(id), donationsNumber);
                this.editor.putInt(String.valueOf(id), donationsNumber + 1);
                this.editor.apply();
                return true;
            } catch (Exception e) {
                return false;
            }

        }
    }

    public boolean saveName(String name, int id) {
        if (!isNameExists(name)) {
            Set<String> set = this.reader.getStringSet("donatedNames", new HashSet<String>());

            set.add(name);

            this.editor.putStringSet("donatedNames", set);
            this.editor.putInt(name, id);
            this.editor.apply();

            return true;
        }

        return false;
    }

    public boolean isNameExists(String name) {
        Set<String> set = this.reader.getStringSet("donatedNames", new HashSet<String>());

        return set.contains(name);
    }

    public boolean isIdExists(int id) {
        Set<String> set = this.reader.getStringSet("donatedIds", new HashSet<String>());

        return set.contains(String.valueOf(id));
    }

    public int getIdOfName(String name) {
        int id = -1;
        this.reader.getInt(name, id);
        return id;
    }

    public ArrayList<String> getIds() {
        Set<String> set = this.reader.getStringSet("donatedIds", new HashSet<String>());
        ArrayList<String> list = new ArrayList<String>(set);
        return list;
    }

    public ArrayList<String> getNames() {
        Set<String> set = this.reader.getStringSet("donatedNames", new HashSet<String>());
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
        } catch (PackageManager.NameNotFoundException e) {
            Log.v("not found", e.toString());
            return "";
        }
    }

    public boolean isFirstTimeLaunch() {
        return reader.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
