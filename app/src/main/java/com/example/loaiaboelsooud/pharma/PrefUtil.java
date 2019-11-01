package com.example.loaiaboelsooud.pharma;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.AccessToken;

import java.util.Calendar;

public class PrefUtil {


    private Activity activity;

    public PrefUtil(Activity activity) {
        this.activity = activity;
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            return true;
        }
        return false;
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

    public void saveExpireDate(Calendar expireDate) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("expire_date", expireDate.getTimeInMillis());
        editor.apply();
    }

    public Calendar getExpireDate() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        long expireDateLongValue = prefs.getLong("expire_date", 0);
        Calendar expireDate = Calendar.getInstance();
        expireDate.setTimeInMillis(expireDateLongValue);
        return expireDate;
    }

    public void saveDrugEyeVersion(Long drugEyeVersion) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("drug_eye_version", drugEyeVersion);
        editor.apply();
    }

    public Long getDrugEyeVersion() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getLong("drug_eye_version", 0);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String splitDateTime(String dateTime) {
        String[] dateTimeArray = dateTime.split(" ");
        String dateTimeSplited = dateTimeArray[0].concat(" ").concat(dateTimeArray[1]).concat(" ").concat(dateTimeArray[2])
                .concat("      ").concat(dateTimeArray[3]).concat(" ").concat(dateTimeArray[4]);
        return dateTimeSplited;

    }

}
