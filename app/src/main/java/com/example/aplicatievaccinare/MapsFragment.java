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
import android.location.Location;
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
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;

    ArrayList<LatLng> pinsLocation = new ArrayList<LatLng>();

    LatLng popesti = new LatLng(44.37733244317854, 26.150120022988286);
    LatLng grozavesti = new LatLng(44.44378696743165, 26.064403963455387);
    LatLng titan = new LatLng(44.4221676193898, 26.180229258037222);

    private int ACCESS_LOCATION_REQUEST_CODE = 10001;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            pinsLocation.add(popesti);
            pinsLocation.add(grozavesti);
            pinsLocation.add(titan);

            for (LatLng pin : pinsLocation) {
                mMap.addMarker(new MarkerOptions().position(pin).title("Centru de Vaccinare")).setIcon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_baseline_cross));
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(popesti, 12F));

            //TODO REZOLVA
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
                    String title = "HAKUNA MATATA";

                    Intent i = new Intent(getActivity(), PinDetailsActivity.class);
                    i.putExtra("title", title);

                    // Implementation for waiting 1 second
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            startActivity(i);
                        }
                    }, 600);

                    //handler.removeCallbacksAndMessages(null);

                    return false;
                }
            });
        }
    };


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
}