package com.example.googlemapfinal;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemapfinal.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.InfoWindowAdapter {

    // Une request code avec un chiffre au hasard qui me servira à réclamer la position
    private final static int LOCATION_REQ_CODE = 456;

    private Button bt; // Mon bouton
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupère le fragment représentant la map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // récupère la carte et fait une synchronisation
        mapFragment.getMapAsync(this);

        // Liaison
        bt = findViewById(R.id.bt);

        bt.setOnClickListener(this);

        // Demander la permission à l'utilisateur d'avoir votre localisation ...............................
        // Si je n'ai pas encore la permission :
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //je demande la permission
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            //Si j'ai la permission et si ma carte est valide
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if(mMap != null) {
                    mMap.setMyLocationEnabled(true);
                }
            }
            else { // Permission refusée
                Toast.makeText(this, "On ne peut pas utiliser la localisation sans la permission", Toast.LENGTH_SHORT).show();
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
    // Où  la carte souvre par défaut
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; // Le pointeur vers notre map

        // Pour styliser la cadre qui apparait au dessus de mon maker
        mMap.setInfoWindowAdapter(this);

        //Si j'ai la permission et si ma carte est valide
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
           // mMap.setOnPoiClickListener(this);
        }
    }


    @Override
    // Quand je clique sur mon bouton "ajouter" il va sur l'autre pointeur
    public void onClick(View v) {
        if (v == bt) {
            // Ajoute le premier marker sur Bruxelles
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker à Sydney"));
            // Centrer la carte sur notre point. Animatecamera pour que le mouvement soit plus fluide
            mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    // Styliser les cadre qui apparait au dessus de mon maker ..............
    @Override
    // La vue que je veux retourner. L'ensemble ( le maker + cadre blanc )
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    // que le cadre blanc
    public View getInfoContents(Marker marker) {
        // mon layout
        View view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);

        TextView tv = view.findViewById(R.id.tv);
        ImageView iv = view.findViewById(R.id.iv);
        // une image
        iv.setImageResource(R.mipmap.ic_launcher);
        // Un texte avec le titre du marker
        tv.setText(marker.getTitle());

        return view;
    }
}