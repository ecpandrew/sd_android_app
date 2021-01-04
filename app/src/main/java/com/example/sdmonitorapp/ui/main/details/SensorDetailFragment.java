package com.example.sdmonitorapp.ui.main.details;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdmonitorapp.R;
import com.example.sdmonitorapp.models.Sensor;
import com.example.sdmonitorapp.ui.main.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SensorDetailFragment extends Fragment implements OnMapReadyCallback {

    private SensorDetailViewModel sensorDetailViewModel;
    private Handler handler = new Handler();
    private GoogleMap mMap;
    private Marker marker;
    private Sensor sensor;
    private EditText temperatura;
    private EditText timestamp;
    private int frequency = 5;


    public static SensorDetailFragment newInstance() {
        return new SensorDetailFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sensorDetailViewModel = new ViewModelProvider(this).get(SensorDetailViewModel.class);

        Bundle bundle = getArguments();
        sensor = bundle.getParcelable("sensor");
        sensorDetailViewModel.setSensor(sensor, requireActivity().getApplicationContext());

        View root = inflater.inflate(R.layout.sensor_detail, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.sensor_map);
        mapFragment.getMapAsync(this);


        temperatura = root.findViewById(R.id.temperatura);
        timestamp = root.findViewById(R.id.timestamp);

//        sensorDetailViewModel.subscribeSensorData();

        sensorDetailViewModel.connect();

        return root;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sensorDetailViewModel.getTemperatura().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                temperatura.setText(s);
                refreshMap(new LatLng(sensor.getLatitude(), sensor.getLongitude()));

            }
        });
        sensorDetailViewModel.getTimestamp().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                timestamp.setText(s);

            }
        });


        // TODO: Use the ViewModel
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Sensor está aqui"));
    }

    private void refreshMap(LatLng coord){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 15));
        marker.setPosition(coord);
    }



}
