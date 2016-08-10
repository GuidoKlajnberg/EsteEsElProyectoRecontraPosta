package com.guidoyuri.a41835420.proyectofinal2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String PARAMETRO2="com.guidoyuri.a41835420.proyectofinal2.PARAMETRO2";
    String ubicac;
    String usuarioReg;
    String usuarioLogin;
    String usuario;
    Button btnPasar;
    private GoogleMap mMap;
    LatLng sydney;
    String estado="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent elintent = getIntent();
        Bundle datos=elintent.getExtras();
        if (datos.getString(Registrarse.PARAMETRO1)==null)
        {
        usuario=datos.getString(Login.PARAMETRO1);
        }
        else
        {
            usuario=datos.getString(Registrarse.PARAMETRO1);
        }
        btnPasar = (Button) findViewById(R.id.btnPasar);
        btnPasar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pasar();
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
Ubicacion ub=new Ubicacion(getApplicationContext());
        sydney=ub.getLocation();
        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(sydney).title("Usted se encuentra aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));


    }

    private void Pasar()
    {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            OkHttpClient client = new OkHttpClient();
            String url = "http://guidoyuri.hol.es/bd/AgregarDireccion.php";
            JSONObject json = new JSONObject();
            json.put("Direccion", sydney.toString());
            json.put("NombreUsuario", usuario);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            Log.d("Response", response.body().string());

        } catch (IOException | JSONException e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getApplicationContext(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(this,Division.class);
        Bundle datos=new Bundle();
        datos.putString(MapsActivity.PARAMETRO2, usuario);
        intent.putExtras(datos);
        startActivity(intent);

    }
}
