package com.banquemisr.www.bmmedical.ui.entity_location;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.banquemisr.www.bmmedical.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EntityMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String ENTITY_NAME = "entity_name";
    private static final String ENTITY_LAT = "entity_lat";
    private static final String ENTITY_LAN = "entity_lan";

    private GoogleMap mMap;

    String name;
    double lat, lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if(null != intent){
            if(intent.hasExtra(ENTITY_LAT) && intent.hasExtra(ENTITY_LAN)){
                name = intent.getStringExtra(ENTITY_NAME);
                lat = intent.getDoubleExtra(ENTITY_LAT,0);
                lan = intent.getDoubleExtra(ENTITY_LAN,0);

            }else {
                finish();
            }
        }else{
            finish();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(lat, lan);
        mMap.addMarker(new MarkerOptions().position(location).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lan), 17.0f));
    }
}
