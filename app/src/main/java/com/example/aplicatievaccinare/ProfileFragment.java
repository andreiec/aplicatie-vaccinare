package com.example.aplicatievaccinare;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aplicatievaccinare.classes.RegisterUser;
import com.example.aplicatievaccinare.singletons.SaveState;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView profileName;
    TextView profileGender;
    TextView profileAddress;
    TextView profileAge;

    TextView appointmentLocation;
    TextView appointmentDate;
    TextView appointmentHour;

    TextView rapelVaccineLocation;
    TextView rapelVaccineDate;
    TextView rapelVaccineHour;

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileName = view.findViewById(R.id.profile_name);
        profileAddress = view.findViewById(R.id.profile_address);
        profileGender = view.findViewById(R.id.profile_gender);
        profileAge = view.findViewById(R.id.profile_age);

        appointmentLocation = view.findViewById(R.id.appointment_location);
        appointmentDate = view.findViewById(R.id.appointment_date);
        appointmentHour = view.findViewById(R.id.appointment_hour);

        rapelVaccineLocation = view.findViewById(R.id.rapel_location);
        rapelVaccineDate = view.findViewById(R.id.rapel_date);
        rapelVaccineHour = view.findViewById(R.id.rapel_hour);

        try {
            RegisterUser user = SaveState.getUserFromMemory(requireContext());

            String name = user.getName();
            String address = "Adresă:\n" + user.getAddress();
            String age = "Vârstă: " + Period.between(LocalDate.parse(user.getBirthDateAsString()), LocalDate.now()).getYears() + " ani";

            String gender;
            if (Integer.parseInt(SaveState.getUserFromMemory(requireContext()).getCNP().substring(0, 1)) % 2 == 1) {
                gender = "Sex: M";
            } else {
                gender = "Sex: F";
            }

            profileName.setText(name);
            profileAddress.setText(address);
            profileGender.setText(gender);
            profileAge.setText(age);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = null;

        try {
            request = new Request.Builder()
                    .url("http://192.168.1.106:8080/appointments/" + SaveState.getUserFromMemory(getContext()).getId())
                    .method("GET", null)
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String r = response.body().string();
                r = r.substring(1, r.length() - 1);

                // "Time slot not found" < 24, [] < 3
                if (r.length() > 24) {
                    Log.i("AAAA", r);
                    String vaccine = r.split(Pattern.quote("}"))[0];
                    String rapel = r.split(Pattern.quote("}"))[1];

                    rapel = rapel.substring(2);

                    Log.i("AAAAAAAAA", vaccine);
                    Log.i("BBBBBBBBB", rapel);

                    // Vaccine
                    String vaccineDate = vaccine.split(",")[0].split(":")[1];
                    String vaccineHour = vaccine.split(",")[1].split(":", 2)[1];
                    String vaccineCenterName = vaccine.split(",")[2].split(":")[1];

                    vaccineDate = vaccineDate.substring(1, vaccineDate.length() - 1);
                    vaccineHour = vaccineHour.substring(1, vaccineHour.length() - 4);
                    vaccineCenterName = vaccineCenterName.substring(1, vaccineCenterName.length() - 1);

                    appointmentDate.setText(vaccineDate);
                    appointmentHour.setText(vaccineHour);
                    appointmentLocation.setText(vaccineCenterName);

                    // Rapel
                    String rapelDate = rapel.split(",")[0].split(":")[1];
                    String rapelHour = rapel.split(",")[1].split(":", 2)[1];
                    String rapelCenterName = rapel.split(",")[2].split(":")[1];

                    rapelDate = rapelDate.substring(1, rapelDate.length() - 1);
                    rapelHour = rapelHour.substring(1, rapelHour.length() - 4);
                    rapelCenterName = rapelCenterName.substring(1, rapelCenterName.length() - 1);

                    rapelVaccineDate.setText(rapelDate);
                    rapelVaccineHour.setText(rapelHour);
                    rapelVaccineLocation.setText(rapelCenterName);
                } else {
                    //TODO NO APPOINTMENT
                }
            }
        });
    }
}