package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RegisterFinalActivity extends AppCompatActivity {

    EditText inputName;
    EditText inputAddress;
    EditText inputCNP;
    Button registerButtonFinal;

    SharedPreferences sp;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);

        String inputEmail = getIntent().getStringExtra("email");
        String inputPassword = getIntent().getStringExtra("pass");

        inputName = findViewById(R.id.register_input_name);
        inputAddress = findViewById(R.id.register_input_address);
        inputCNP = findViewById(R.id.register_input_CNP);
        registerButtonFinal = findViewById(R.id.register_button_final);

        // Cel mai probabil o sa uit tot proiectul dar macar il termin :)
        mContext = this;

        // Save user on local storage
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        registerButtonFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call API to create user
                new CreateUserHttpReq().execute(inputEmail, inputPassword, inputName.getText().toString(), "2000-01-01", inputAddress.getText().toString(), inputCNP.getText().toString());
            }
        });
    }

    public class CreateUserHttpReq extends AsyncTask<String, Void, Void> {

        User user = null;

        @Override
        protected Void doInBackground(String... params) {
            try{
                String apiUrl = "http://192.168.1.106:8080/users/register";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Make post request
                UserCreate newUser = new UserCreate(params[0], params[1], params[2], params[3], params[4], params[5]);

                // Save returned info into user to store to local storage (cache)
                user = restTemplate.postForObject(apiUrl, newUser, User.class);

                // After user is registered, save data to local storage
                SharedPreferences.Editor editor = sp.edit();
                editor.putLong("id", user.getId());
                editor.putString("email", user.getEmail());
                editor.putString("name", user.getName());
                editor.putString("birthDate", user.getBirthDate().toString());
                editor.putString("address", user.getAddress());
                editor.apply();

            } catch (Exception e) {
                Log.e("", Arrays.toString(e.getStackTrace()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void ac) {
            super.onPostExecute(ac);

            Intent i = new Intent(mContext, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Change activity to login page
            startActivity(i);
        }

    }
}