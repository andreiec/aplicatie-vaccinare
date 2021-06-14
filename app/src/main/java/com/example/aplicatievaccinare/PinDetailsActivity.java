package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aplicatievaccinare.singletons.SaveState;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

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

    TextView dateText;
    TextView hourText;

    Button dateButton;
    Button hourButton;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    String appointmentDay;
    String appointmentHour;

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

        dateText = findViewById(R.id.appointment_set_date_text);
        hourText = findViewById(R.id.appointment_set_hour_text);

        dateButton = findViewById(R.id.appointment_set_date_button);
        hourButton = findViewById(R.id.appointment_set_hour_button);

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

        // Date Click
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PinDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        appointmentDay = year + "-" + (month + 1) + "-" + dayOfMonth;
                        dateText.setText(appointmentDay);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(PinDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        appointmentHour = hourOfDay + ":" + minute + ":00";
                        hourText.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        // Appointment Click
        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = null;

                try {
                    body = RequestBody.create(mediaType, "{\r\n    \"patientId\": " + SaveState.getUserFromMemory(getBaseContext()).getId() + ",\r\n    \"date\": \"" + appointmentDay +"\",\r\n    \"time\": \"" + appointmentHour + "\",\r\n    \"vaccineCenterId\": " + id + "\r\n}");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Request request = new Request.Builder()
                        .url("http://" + BuildConfig.SERVER_IP + ":8080/appointments/add")
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