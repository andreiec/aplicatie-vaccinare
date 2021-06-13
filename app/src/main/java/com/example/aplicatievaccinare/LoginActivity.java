package com.example.aplicatievaccinare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aplicatievaccinare.classes.LoginUser;
import com.example.aplicatievaccinare.classes.UserAuth;
import com.example.aplicatievaccinare.singletons.SaveState;
import com.fasterxml.jackson.databind.ser.Serializers;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                new LoginUserHttpReq().execute(inputEmail.getText().toString(), inputPass.getText().toString(), "password", "web");
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

    public class LoginUserHttpReq extends AsyncTask<String, Void, Void> {

        LoginUser user = null;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(String... params) {
            try{
                String apiUrl = "http://192.168.1.106:8080/oauth/token";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "username=" + params[0] + "&password=" + params[1] + "&grant_type=" + params[2] + "&scope=" + params[3]);
                Request request = new Request.Builder().url(apiUrl).method("POST", body).addHeader("Authorization", "Basic YmFja2VuZDo=").addHeader("Content-Type", "application/x-www-form-urlencoded").build();
                Response response = client.newCall(request).execute();

                // Save returned info into login user to save token
                //user = restTemplate.postForObject(apiUrl, newUser, LoginUser.class);

                Log.i("???", String.valueOf(response.body()));
//                String plainCreds = "backend:";
//                byte[] plainCredsBytes = plainCreds.getBytes();
//                byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
//                String base64Creds = new String(base64CredsBytes);
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Authorization", "Basic " + base64Creds);
//                headers.setContentType(MediaType.APPLICATION_JSON);
//                Log.i("COD", base64Creds);
//
//                //JSONObject requestJson = new JSONObject();
//
//                //TODO AUTENTIFICARE CU 'backend' si '' DOAMNE AJUTA
//                HttpEntity<UserAuth> request = new HttpEntity<UserAuth>(newUser, headers);
//                ResponseEntity<LoginUser> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, LoginUser.class);
                //user = response.getBody();
                Log.i("LOGGED IN", user.getToken());

                // After user is logged in, save token to local storage
                SaveState.token = user.getToken();
                SaveState.saveTokenToMemory(mContext);
                Log.i("TOKEN", SaveState.token);

            } catch (Exception e) {
                Log.e("", Arrays.toString(e.getStackTrace()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void ac) {
            super.onPostExecute(ac);

            Intent i = new Intent(mContext, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Change activity to login page
            startActivity(i);
            finish();
        }
    }
}