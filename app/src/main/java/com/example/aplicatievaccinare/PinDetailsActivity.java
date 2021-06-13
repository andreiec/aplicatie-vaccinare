package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicatievaccinare.singletons.SaveState;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PinDetailsActivity extends AppCompatActivity {

    TextView markerText;
    TextView markerAddress;
    TextView markerAvailable;
    TextView markerType;

    Button appointmentButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_details);

        markerText = findViewById(R.id.markerText);
        markerAddress = findViewById(R.id.markerAddress);
        markerAvailable = findViewById(R.id.markerAvailable);
        markerType = findViewById(R.id.markerType);
        appointmentButton = findViewById(R.id.appointment_button);

        String title = getIntent().getStringExtra("title");
        String address = getIntent().getStringExtra("address");
        String available = getIntent().getStringExtra("available");
        String type = getIntent().getStringExtra("type");

        long id = getIntent().getLongExtra("id", 0);

        if (type.equals("1")) {
            type = "Astra Zeneca";
        }

        if (type.equals("2")) {
            type = "Pfizer";
        }

        if (type.equals("3")) {
            type = "Moderna";
        }

        if (type.equals("4")) {
            type = "Johnson&Johnson";
        }

        markerText.setText(title);
        markerAddress.setText("Adresa\n" + address);
        markerAvailable.setText("Doze disponibile\n" + available);
        markerType.setText("Vaccin\n" + type);

        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = null;

                try {
                    body = RequestBody.create(mediaType, "{\r\n    \"patientId\": " + SaveState.getUserFromMemory(getBaseContext()).getId() + ",\r\n    \"date\": \"2020-07-31\",\r\n    \"time\": \"22:00:00\",\r\n    \"vaccineCenterId\": " + id + "\r\n}");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Request request = new Request.Builder()
                        .url("http://192.168.1.106:8080/appointments/add")
                        .method("POST", body)
                        .addHeader("Authorization", "Bearer " + SaveState.token)
                        .addHeader("Content-Type", "application/json")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String r = response.body().string();
                        Log.i("DA", r);
                        String toastMessage;

                        if (r.equals("User already appointed")) {
                            toastMessage = "Deja aveți o programare!";
                        } else {
                            toastMessage = "Programare realizată!";
                        }

                        runOnUiThread(() -> {
                            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                            toast.show();
                        });

                        finish();
                    }
                });
            }
        });
    }
}