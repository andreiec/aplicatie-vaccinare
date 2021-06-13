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
import com.example.aplicatievaccinare.classes.RegisterUser;
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

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;

import okhttp3.Call;
import okhttp3.Callback;
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
                String apiUrl = "http://192.168.1.106:8080/oauth/token";

                // Magic
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "username=" + inputEmail.getText().toString() + "&password=" + inputPass.getText().toString() + "&grant_type=password&scope=web");
                Request request = new Request.Builder().url(apiUrl).method("POST", body).addHeader("Authorization", "Basic YmFja2VuZDo=").addHeader("Content-Type", "application/x-www-form-urlencoded").build();

                // Call API to get auth token
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseString = response.body().string();

                            String access_token = responseString.split(",")[0].split(":")[1];
                            access_token = access_token.substring(1, access_token.length() - 1);

                            Log.d("Logged in with token", access_token);
                            SaveState.token = access_token;
                            SaveState.saveTokenToMemory(mContext);

                            Intent i = new Intent(mContext, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Request user info after successful login
                            Request userInfoRequest = new Request.Builder().url("http://192.168.1.106:8080/users/getByEmail?userEmail=" + inputEmail.getText().toString()).method("GET", null)
                                    .addHeader("Authorization", "Bearer " + access_token).build();

                            // Call API to get info about user after first api call (Continue login process)
                            client.newCall(userInfoRequest).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String infoResponseString = response.body().string();

                                    infoResponseString = infoResponseString.substring(1, infoResponseString.length() - 1);

                                    Log.i("User Info", infoResponseString);

                                    // Nu stiu cum, dar merge (JSON Parse)
                                    String userID = infoResponseString.split(",")[0].split(":")[1];
                                    String userMail = infoResponseString.split(",")[1].split(":")[1];
                                    String userName = infoResponseString.split(",")[2].split(":")[1];
                                    String userBirthDate = infoResponseString.split(",")[3].split(":")[1];
                                    String userAddress = infoResponseString.split(",")[4].split(":")[1];
                                    String userCNP = infoResponseString.split(",")[5].split(":")[1];

                                    userMail = userMail.substring(1, userMail.length() - 1);
                                    userName = userName.substring(1, userName.length() - 1);
                                    userBirthDate = userBirthDate.substring(1, userBirthDate.length() - 1);
                                    userAddress = userAddress.substring(1, userAddress.length() - 1);
                                    userCNP = userCNP.substring(1, userCNP.length() - 1);

                                    RegisterUser user = new RegisterUser(Long.parseLong(userID), userMail, userName, Date.valueOf(userBirthDate), userAddress, userCNP);

                                    // Save details to storage
                                    SaveState.saveUserToMemory(getBaseContext(), user);

                                    // Change activity to login page
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                    }
                });
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