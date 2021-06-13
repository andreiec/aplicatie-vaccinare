package com.example.aplicatievaccinare;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class MapsFragment extends Fragment {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;

    private ArrayList<VaccineCenter> vaccineCenters = new ArrayList<>();

    private int ACCESS_LOCATION_REQUEST_CODE = 10001;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            new HttpMapPinRequest().execute();

            // Default camera position
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(44.43, 26.1), 12F));

            //TODO rezolva Style ul
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
                }
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    // Loop through all markers to find the right one
                    VaccineCenter currentVaccineCenter = new VaccineCenter();

                    for (VaccineCenter vaccineCenter : vaccineCenters) {
                        if (vaccineCenter.getName().equals(marker.getTitle())) {
                            currentVaccineCenter.setId(vaccineCenter.getId());
                            currentVaccineCenter.setName(vaccineCenter.getName());
                            currentVaccineCenter.setAddress(vaccineCenter.getAddress());
                            currentVaccineCenter.setDosesAvailable(vaccineCenter.getDosesAvailable());
                            currentVaccineCenter.setLatitude(vaccineCenter.getLatitude());
                            currentVaccineCenter.setLongitude(vaccineCenter.getLongitude());
                            currentVaccineCenter.setVaccineTypeBrand(vaccineCenter.getVaccineTypeBrand());
                            break;
                        }
                    }

                    try {
                        Intent i = new Intent(getActivity(), PinDetailsActivity.class);
                        i.putExtra("id", currentVaccineCenter.getId());
                        i.putExtra("title", currentVaccineCenter.getName());
                        i.putExtra("available", String.valueOf(currentVaccineCenter.getDosesAvailable()));
                        i.putExtra("type", currentVaccineCenter.getVaccineTypeBrand());
                        i.putExtra("address", currentVaccineCenter.getAddress());

                        // Implementation for waiting 0.5 second
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                startActivity(i);
                            }
                        }, 500);
                    } catch (Exception e) {
                        Log.i("Not found", Arrays.toString(e.getStackTrace()));
                    }


                    //handler.removeCallbacksAndMessages(null);

                    return false;
                }
            });
        }
    };

    private void addMarkersAfterAPICall() {
        for (VaccineCenter vaccineCenter : vaccineCenters) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(vaccineCenter.getLatitude(), vaccineCenter.getLongitude())).title(vaccineCenter.getName())).setIcon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_baseline_cross));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    // Implement Vector to Bitmap for pin
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_pin);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());

        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(18, 18, vectorDrawable.getIntrinsicWidth() + 10, vectorDrawable.getIntrinsicHeight() + 10);

        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        background.draw(canvas);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
            } else {
                Log.i("No location", "Access");
            }
        }
    }

    // API Request to gett all vaccine centers
    private class HttpMapPinRequest extends AsyncTask<Void, Void, VaccineCenter[]> {

        @Override
        protected VaccineCenter[] doInBackground(Void... params) {

            try{
                String apiUrl = "http://192.168.1.106:8080/centers/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                VaccineCenter[] vaccineCenters = restTemplate.getForObject(apiUrl, VaccineCenter[].class);

                return vaccineCenters;
            } catch (Exception e) {
                Log.e("", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(VaccineCenter[] vc) {
            super.onPostExecute(vc);
            vaccineCenters.clear();

            if (vc != null) {
                for (VaccineCenter center : vc){
                    Log.i("Vaccine center name", String.valueOf(center.getName()));
                    vaccineCenters.add(center);
                }
            } else {
                Log.i("Client Error", "No vaccine center in that range");
            }

            addMarkersAfterAPICall();
        }
    }
}