package edu.nasredine.cheniki.geolocalisation;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    TextView location_text_view;
    Button btn_search;
    Button btn_select;
    EditText search_edit_view;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_select = (Button) findViewById(R.id.btn_select);
        search_edit_view = (EditText) findViewById(R.id.searchView1);
        location_text_view = (TextView) findViewById(R.id.location_text_view);


        btn_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String texte = search_edit_view.getText().toString();

                Geocoder geocoder = new Geocoder(getBaseContext());
                List<Address> addresses = null;

                try {
                    // Getting a maximum of 3 Address that matches the input text
                    addresses = geocoder.getFromLocationName(texte, 3);
                    if (addresses != null && !addresses.equals(""))
                        afficherSurMap(addresses);

                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, "Problème geo, pas de résultat", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latitude", latitude);
                resultIntent.putExtra("longitude", longitude);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
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

        // TODO: lancer la map pointant sur la position de l'utilisateur
        LatLng tours = new LatLng(47.3941, 0.6848);

        mMap.addMarker(new MarkerOptions().position(tours).title("Marker in Tours"));
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tours));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        mMap.setOnPoiClickListener(this);


    }

    protected void afficherSurMap(List<Address> addresses) {

        Address address = (Address) addresses.get(0);
        longitude = address.getLongitude();
        latitude = address.getLatitude();
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        String addressText = String.format("%s, %s", address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", address.getCountryName());

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title(addressText);

        mMap.clear();
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        location_text_view.setText("Latitude:" + address.getLatitude() + ", Longitude:"
                + address.getLongitude());
    }


    /**
     * Cette méthode est lancée quand l'utilisateur clique sur un point d'intérêt
     *
     * @param poi
     */

    @Override
    public void onPoiClick(PointOfInterest poi) {
        latitude = poi.latLng.latitude;
        longitude = poi.latLng.longitude;

        location_text_view.setText("Clicked: " + poi.name
                + "\nLatitude:" + poi.latLng.latitude
                + " Longitude:" + poi.latLng.longitude);

    }
}
