package com.example.aplicatievaccinare.singletons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.aplicatievaccinare.classes.RegisterUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class SaveState {

    public static RegisterUser user;
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
    public static void saveUserToMemory(Context context, RegisterUser rUser) {
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        user = rUser;

        editor.putLong("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putString("birthDate", user.getBirthDateAsString());
        editor.putString("address", user.getAddress());
        editor.putString("cnp", user.getCNP());
        editor.apply();
    }

    // To save, overwrite token and call saveTokenToMemory
    public static void saveTokenToMemory(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    @SuppressLint("SimpleDateFormat")
    public static RegisterUser getUserFromMemory(Context context) throws ParseException {
        RegisterUser rUser = new RegisterUser();
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        rUser.setId(sp.getLong("id", -1));
        rUser.setAddress(sp.getString("address", ""));
        rUser.setBirthDate(Date.valueOf(sp.getString("birthDate", "")));
        rUser.setEmail(sp.getString("email", ""));
        rUser.setName(sp.getString("name", ""));
        rUser.setCNP(sp.getString("cnp", ""));

        // Update user if Needed
        user = rUser;
        return rUser;
    }
}
