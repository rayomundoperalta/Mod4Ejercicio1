package mx.peta.mod4ejercicio1.utileria;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by rayo on 6/21/16.
 */
public class Preferences {
    private static final String FILE_NAME = "ejercicio2_pref";
    private final SharedPreferences sp;

    public Preferences(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /* Escribimos un rutina general para guardar pares (key, value) en las preferencias */
    public void saveStringItem(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getStringItem(String key) {
        String value = sp.getString(key, null);
        if (TextUtils.isEmpty(value))
            return null;
        else
            return value;
    }

    public void saveLongItem(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public long getLong(String key) {
        return sp.getLong(key, -1);
    }
}
