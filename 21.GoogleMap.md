# Google map

Lien vers le projet : https://github.com/marieThielens/googleMapAndroid/tree/master

Créer un nouveau projet : au lieu de choisir empty on va choisir googleMap

Le programme va souvrir sur un fichier google_map_api  . Il faut prendre l'url (http...) et la copier dans un navigateur.
On arrive sur Google Cloud Platform

- dans créer un projet : donner un nom
- Accepter toutes les conditions d'utilisation
- Ok
- Cliquer sur le bouton : Créer la clé API ( pour l'instant c'est une clé de developpement. Ce sera à refair pour production)

- Copier la clé et la mettre dans le fichier google_map_api.xml ( à la place de YOUR_KEY_HERE)
`<string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_KEY_HERE</string>`

- On a un layout activity_maps.xml qui contien un seul fragment (rien à changer )

- On a un fichier MapsActivity.java

```java
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

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
        mMap = googleMap; // Le pointeur vers notre map

        // Ajoute le premier marker sur Sydney
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
```

Dans le manifeste on a bien la permission
` <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />`

# Manipuler les makers ( le pointeur )

- Ajouter des composants graphique à notre carte
- Ajouter des makers ( un pointeur ) sur la carte pour déplacer avec animation pour qu'elle se centre sur un point en particulier

- le RelativeLayout permet la superposition (que le bouton soit au dessus de la map)

## activity_maps.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >
    <fragment 
        xmlns:map="http://schemas.android.com/apk/res-auto"
        
        android:id="@+id/map"
        android:name="com.google.android.gms.maps.SupportMapFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MapsActivity" />
    <Button
        android:id="@+id/bt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:text="Ajouter"
        />
</RelativeLayout>
```

# Je vais styliser le cadre qui apparraitra au dessus de mon maker

Créer un fichier maker_layout.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <ImageView
        android:layout_width="30dp"
        android:layout_height="30dp"
        android:id="@+id/iv"
        android:layout_margin="10dp"
        />
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Nouveau Texte"
        android:id="@+id/tv"
        android:gravity="center_vertical"
        />

</LinearLayout>
```

## fichier MainActivity 

```java
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.InfoWindowAdapter {

    // Une request code avec un chiffre au hasard qui me servira à réclamer la position
    private final static int LOCATION_REQ_CODE = 456;

    private Button bt; // Mon bouton
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

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
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //je demande la permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQ_CODE) {
            //Si j'ai la permission et si ma carte est valide
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if(mMap != null) {
                    mMap.setMyLocationEnabled(true);
                }
            }
            else { // Permission refusée
                Toast.makeText(this, "On ne peut pas utiliser la localisation sans la permission", Toast.LENGTH_SHORT).show();
            }
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
            if(mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
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
```
