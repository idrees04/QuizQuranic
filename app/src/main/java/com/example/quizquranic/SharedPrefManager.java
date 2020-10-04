package com.example.quizquranic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "PreferredName";
    private static final String KEY_TOKEN = "Token";
    private static final String KEY_ID = "UserId";
    private static final String KEY_ISSUCCESS = "IsSuccess";
    private static final String KEY_ERROR_MESSAGE = "ErrorMessage";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREF_NAME, user.getPreferredName_());
        editor.putString(KEY_TOKEN, user.getToken_());
        editor.putInt(KEY_ID, user.getUserId_());
        editor.putString(KEY_ERROR_MESSAGE, user.getErrorMessage_());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHARED_PREF_NAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(SHARED_PREF_NAME, null),
                sharedPreferences.getString(KEY_TOKEN, null),
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getBoolean(KEY_ISSUCCESS, false),
                sharedPreferences.getString(KEY_ERROR_MESSAGE, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}