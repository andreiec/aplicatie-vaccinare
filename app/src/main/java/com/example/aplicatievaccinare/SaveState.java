package com.example.aplicatievaccinare;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SaveState {

    public static User user;
    public static String token;

    private static SaveState instance = null;

    private SaveState() { }

    public static SaveState getInstance() {
        if (instance == null) {
            return new SaveState();
        }
        return instance;
    }

    // To save, overwrite user and call saveUserToMemory
    public static void saveUserToMemory(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putString("birthDate", user.getBirthDate().toString());
        editor.putString("address", user.getAddress());
        editor.apply();
        Log.i(user.getAddress(), user.getEmail());
    }

    // To save, overwrite token and call saveTokenToMemory
    public static void saveTokenToMemory(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
