package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPass;
    EditText inputPassConfirm;

    Button registerButton;
    TextView changeToLoginText;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.register_input_mail);
        inputPass = findViewById(R.id.register_input_password);
        inputPassConfirm = findViewById(R.id.register_input_password_confirm);

        registerButton = findViewById(R.id.register_button);
        changeToLoginText = findViewById(R.id.register_change_text);

        // Cel mai probabil o sa uit tot proiectul dar macar il termin :)
        mContext = this;

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Minimal check to see if passwords match
                if(inputPass.getText().toString().equals(inputPassConfirm.getText().toString())) {
                    // Open next page of registration
                    try {
                        Intent i = new Intent(mContext, RegisterFinalActivity.class);
                        i.putExtra("email", inputEmail.getText().toString());
                        i.putExtra("pass", inputPass.getText().toString());
                        mContext.startActivity(i);
                    } catch (Exception e) {
                        Log.i("Not found", Arrays.toString(e.getStackTrace()));
                    }
                }
            }
        });

        changeToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(i);
                finish();
            }
        });
    }
}