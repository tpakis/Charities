package greek.dev.challenge.charities.utilities;

/*
 * Created by geoDev on 28/12/2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("UnusedReturnValue")
public class CharitiesPreferences {

    private static final String PREF_NAME = "charities-prefs";

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

}
