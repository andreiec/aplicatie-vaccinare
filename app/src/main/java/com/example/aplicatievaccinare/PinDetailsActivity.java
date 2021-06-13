package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class PinDetailsActivity extends AppCompatActivity {

    TextView markerText;
    TextView markerAddress;
    TextView markerAvailable;
    TextView markerType;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_details);

        markerText = findViewById(R.id.markerText);
        markerAddress = findViewById(R.id.markerAddress);
        markerAvailable = findViewById(R.id.markerAvailable);
        markerType = findViewById(R.id.markerType);

        String title = getIntent().getStringExtra("title");
        String address = getIntent().getStringExtra("address");
        String available = getIntent().getStringExtra("available");
        String type = getIntent().getStringExtra("type");

        //TODO Adaugare buton programare
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
    }
}