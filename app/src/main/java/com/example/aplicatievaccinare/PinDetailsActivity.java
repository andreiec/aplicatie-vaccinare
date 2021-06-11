package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PinDetailsActivity extends AppCompatActivity {

    TextView markerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_details);
        markerText = findViewById(R.id.marker);
        String title = getIntent().getStringExtra("title");
        markerText.setText(title);
    }
}