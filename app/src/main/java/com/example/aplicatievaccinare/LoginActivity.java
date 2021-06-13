package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPass;

    Button loginButton;
    TextView changeToRegisterText;

    Context mContext;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.login_input_mail);
        inputPass = findViewById(R.id.login_input_password);

        loginButton = findViewById(R.id.login_button);
        changeToRegisterText = findViewById(R.id.login_change_text);

        mContext = this;

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        changeToRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RegisterActivity.class);
                mContext.startActivity(i);
                finish();
            }
        });
    }
}