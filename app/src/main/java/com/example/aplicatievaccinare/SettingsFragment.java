package com.example.aplicatievaccinare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicatievaccinare.classes.RegisterUser;
import com.example.aplicatievaccinare.singletons.SaveState;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    EditText inputName;
    EditText inputAddress;
    EditText inputCNP;

    Button settingsButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputName = view.findViewById(R.id.settings_input_name);
        inputAddress = view.findViewById(R.id.settings_input_address);
        inputCNP = view.findViewById(R.id.settings_input_CNP);
        settingsButton = view.findViewById(R.id.settings_button);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    RegisterUser user = SaveState.getUserFromMemory(getContext());

                    String dataNastere;
                    if (Integer.parseInt(inputCNP.getText().toString().substring(0, 1)) < 4) {
                        dataNastere = "19" + inputCNP.getText().toString().substring(1, 3) + "-" + inputCNP.getText().toString().substring(3, 5) + "-" + inputCNP.getText().toString().substring(5, 7);
                    } else {
                        dataNastere = "20" + inputCNP.getText().toString().substring(1, 3) + "-" + inputCNP.getText().toString().substring(3, 5) + "-" + inputCNP.getText().toString().substring(5, 7);
                    }

                    OkHttpClient client = new OkHttpClient().newBuilder().build();
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "{\r\n    \"email\": \"" + user.getEmail() + "\",\r\n    \"name\": \"" + inputName.getText().toString() +"\",\r\n    \"birthDate\": \"" + dataNastere + "\",\r\n    \"address\": \"" + inputAddress.getText().toString() +"\",\r\n    \"cnp\": \"" + inputCNP.getText().toString() + "\"\r\n}");
                    Request request = new Request.Builder()
                            .url("http://192.168.1.106:8080/users/edit/" + user.getId())
                            .method("PUT", body)
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
                            RegisterUser newUser = new RegisterUser(user.getId(), user.getEmail(), inputName.getText().toString(), Date.valueOf(dataNastere), inputAddress.getText().toString(), inputCNP.getText().toString());
                            SaveState.saveUserToMemory(getContext(), newUser);

                            Log.i("PUT", newUser.getEmail());
                            Log.i("PUT", inputName.getText().toString());
                            Log.i("PUT", dataNastere);
                            Log.i("PUT", inputAddress.getText().toString());
                            Log.i("PUT",  inputCNP.getText().toString());

                            getActivity().runOnUiThread(() -> {
                                Toast toast = Toast.makeText(getContext(), "Datele au fost actualizate!", Toast.LENGTH_LONG);
                                toast.show();
                            });
                        }
                    });

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}