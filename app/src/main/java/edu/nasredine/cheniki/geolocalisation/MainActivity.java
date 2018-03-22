package edu.nasredine.cheniki.geolocalisation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et_longitude;
    EditText et_latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_longitude = (EditText) findViewById(R.id.tv_longitude);
        et_latitude = (EditText) findViewById(R.id.tv_latitude);

        Button btn_google_maps = (Button) findViewById(R.id.btn_google_maps);

        btn_google_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_map = new Intent(getApplicationContext(), MapsActivity.class);
                // TODO: ajouter les coordonnées latitude, longitude récupérées par le service
                //intent_map.putExtra("latitude", "");
                startActivityForResult(intent_map, 1);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1): {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    double latitude = data.getDoubleExtra("latitude", 0);
                    double longitude = data.getDoubleExtra("longitude", 0);
                    et_latitude.setText(latitude + "");
                    et_longitude.setText(longitude + "");

                }
                break;
            }
        }
    }
}
