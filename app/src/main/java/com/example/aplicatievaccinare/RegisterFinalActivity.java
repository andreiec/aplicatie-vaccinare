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
import android.widget.Toast;

import com.example.aplicatievaccinare.classes.RegisterUser;
import com.example.aplicatievaccinare.classes.UserCreate;
import com.example.aplicatievaccinare.singletons.SaveState;

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
                String dataNastere;
                if (Integer.parseInt(inputCNP.getText().toString().substring(0, 1)) < 4) {
                    dataNastere = "19" + inputCNP.getText().toString().substring(1, 3) + "-" + inputCNP.getText().toString().substring(3, 5) + "-" + inputCNP.getText().toString().substring(5, 7);
                } else {
                    dataNastere = "20" + inputCNP.getText().toString().substring(1, 3) + "-" + inputCNP.getText().toString().substring(3, 5) + "-" + inputCNP.getText().toString().substring(5, 7);
                }
                Log.i("A", inputEmail);
                Log.i("B", inputPassword);
                Log.i("C", inputName.getText().toString());
                Log.i("D", dataNastere);
                Log.i("E", inputAddress.getText().toString());
                Log.i("F",  inputCNP.getText().toString());
                new CreateUserHttpReq().execute(inputEmail, inputPassword, inputName.getText().toString(), dataNastere, inputAddress.getText().toString(), inputCNP.getText().toString());
            }
        });
    }

    public class CreateUserHttpReq extends AsyncTask<String, Void, Void> {

        RegisterUser user = null;

        @Override
        protected Void doInBackground(String... params) {
            try{
                String apiUrl = "http://192.168.1.106:8080/users/register";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                // Make post request
                UserCreate newUser = new UserCreate(params[0], params[1], params[2], params[3], params[4], params[5]);

                // Save returned info into user to store to local storage (cache)
                user = restTemplate.postForObject(apiUrl, newUser, RegisterUser.class);

                // After user is registered, save data to local storage (Dont do it for register, do it for login)
//                SaveState.user = user;
//                SaveState.saveUserToMemory(mContext);

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

            runOnUiThread(() -> {
                Toast toast = Toast.makeText(getApplicationContext(), "Înregistrare realizată!", Toast.LENGTH_SHORT);
                toast.show();
            });

            // Change activity to login page
            startActivity(i);
            finish();
        }

    }
}