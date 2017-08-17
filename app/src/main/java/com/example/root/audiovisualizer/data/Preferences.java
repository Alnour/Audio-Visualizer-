package com.example.root.audiovisualizer.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by root on 23/06/17.
 */

public class Preferences {
    public static final String APP_KEY = "APP_KEY";
    public static final String LAST_READ_TIME = "time_to_live";

    private static Preferences preferences;
    private SharedPreferences sharedPreferences;
    public Preferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_KEY,Context.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context){
        if(preferences == null)
            preferences = new Preferences(context);
        return preferences;
    }



    public <T> void saveItem(String key, T item){

        Gson gson = new Gson();
        String serialized = gson.toJson(item, item.getClass());
        preferences.sharedPreferences.edit().putString(key, serialized).apply();
        updateLastRead(key);
    }

    public <T> T getSavedItem(String key, Class itemClass){
        Gson gson = new Gson();
        String serialized = preferences.sharedPreferences.getString(key, "");
        if(TextUtils.isEmpty(serialized))
            return null;
        //TODO ==> think of a better algorithm to update the last read
//        updateLastRead(key);
        return (T) gson.fromJson(serialized, itemClass);
    }

    private void updateLastRead(String key) {
        preferences.sharedPreferences.edit().putLong(key+ LAST_READ_TIME,
                System.currentTimeMillis()).apply();
    }

    public  long lastReadTime(String key){
        return preferences.sharedPreferences.getLong(key + LAST_READ_TIME, 0);
    }

    public void clearCache(){
        sharedPreferences.edit().clear().apply();
    }
}
