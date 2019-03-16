package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefUtil {


    private Activity activity;

    public PrefUtil(Activity activity) {
        this.activity = activity;
    }


    public void saveAccessToken(String token) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fb_access_token", token);
        editor.apply(); // This line is IMPORTANT !!!
    }

    public String getToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getString("fb_access_token", null);
    }

    public void clearToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply(); // This line is IMPORTANT !!!
    }

    public void saveFacebookUserInfo(String name, String email, String qualification, String avatar, String job, String city, long expiresIn) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("qualification", qualification);
        editor.putString("avatar", avatar);
        editor.putString("job", job);
        editor.putString("city", city);
        editor.putLong("expires_in", expiresIn);
        editor.apply(); // This line is IMPORTANT !!!
        Log.d("MyApp", "Shared Name : " + name + "\nEmail : " + email);
    }

    public User getFacebookUserInfo() {
        User user = new User();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        user.setName(prefs.getString("name", null));
        user.setEmail(prefs.getString("email", null));
        user.setAvatar(prefs.getString("avatar", null));
        user.setQualification(prefs.getString("qualification", null));
        user.setJob(prefs.getString("job", null));
        user.setCity(prefs.getString("city", null));
        return user;
    }

}
