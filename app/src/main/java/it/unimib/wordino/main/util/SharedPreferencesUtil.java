package it.unimib.wordino.main.util;
import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Utility class to read and write data using SharedPreferences API.
 * Doc can be read here: https://developer.android.com/training/data-storage/shared-preferences
 */
public class SharedPreferencesUtil {

    private final Application application;

    public SharedPreferencesUtil(Application application) {
        this.application = application;
    }

    /**
     * Writes a String value using SharedPreferences API.
     * @param sharedPreferencesFileName The name of file where to write data.
     * @param key The key associated with the value to write.
     * @param value The value to write associated with the key.
     */
    public void writeStringData(String sharedPreferencesFileName, String key, String value) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Writes a boolean value using SharedPreferences API.
     * @param sharedPreferencesFileName The name of file where to write data.
     * @param key The key associated with the value to write.
     * @param value The value to write associated with the key.
     */
    public void writeBooleanData(String sharedPreferencesFileName, String key, boolean value) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Writes a set of String values using SharedPreferences API.
     * @param sharedPreferencesFileName The name of file where to write data.
     * @param key The key associated with the value to write.
     * @param value The value to write associated with the key.
     */
    public void writeStringSetData(String sharedPreferencesFileName, String key, Set<String> value) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * Returns the String value associated with the key passed as argument
     * using SharedPreferences API.
     * @param sharedPreferencesFileName The name of file where to read the data.
     * @param key The key associated with the value to read.
     * @return The String value associated with the key passed as argument.
     */
    public String readStringData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    /**
     * Returns the boolean value associated with the key passed as argument
     * using SharedPreferences API.
     * @param sharedPreferencesFileName The name of file where to read the data.
     * @param key The key associated with the value to read.
     * @return The boolean value associated with the key passed as argument.
     */
    public boolean readBooleanData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                MODE_PRIVATE);
        if  (sharedPref.contains(key)) {
            return sharedPref.getBoolean(key, false);
        }else {
            return false;
        }
    }

    /**
     * Returns the set of String values associated with the key passed as argument
     * using SharedPreferences API.
     * @param sharedPreferencesFileName The name of file where to read the data.
     * @param key The key associated with the value to read.
     * @return The set of String values associated with the key passed as argument.
     */
    public Set<String> readStringSetData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                MODE_PRIVATE);
        return sharedPref.getStringSet(key, null);
    }

    /**
     * Deletes data saved in files created with SharedPreferences API.
     * @param sharedPreferencesFileName The file name where the information is saved.
     */
    public void deleteAll(String sharedPreferencesFileName) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

}
