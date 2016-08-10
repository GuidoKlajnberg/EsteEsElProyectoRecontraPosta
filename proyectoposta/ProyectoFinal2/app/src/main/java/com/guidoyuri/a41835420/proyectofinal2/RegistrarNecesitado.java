package com.guidoyuri.a41835420.proyectofinal2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegistrarNecesitado extends AppCompatActivity {
    public static final String PARAMETRO1 = "com.guidoyuri.a41835420.proyectofinal2.PARAMETRO1";
    public static final String PARAMETRO2 = "com.guidoyuri.a41835420.proyectofinal2.PARAMETRO2";
    TextView txtNombre;
    TextView txtSexo;
    TextView txtDescripcion;
    TextView txtUsuario;
    EditText edtNombre;
    EditText edtDescripcion;
    RadioButton rdbMasculino;
    RadioButton rdbFemenino;
    Button btnEnviar;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_necesitado);
        ObtenerReferencia();
        Intent elintent = getIntent();
        Bundle datos=elintent.getExtras();
        usuario=datos.getString(Division.PARAMETRO4);
        txtUsuario.setText(usuario);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Enviar();
            }
        });
    }
    private void Enviar()
    {
        String sexo;
        if (rdbMasculino.isChecked())
        {
            sexo="Masculino";
        }
        else
        {
            sexo="Femenino";
        }
        if ((edtNombre.getText().toString()=="")||(edtDescripcion.getText().toString()==""))
        {
            Toast.makeText(getApplicationContext(),"No puede dejar campos vacios",Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                OkHttpClient client = new OkHttpClient();
                String url = "http://guidoyuri.hol.es/bd/AgregarNecesitado.php";
                JSONObject json = new JSONObject();
                json.put("Nombre", edtNombre.getText().toString());
                json.put("Sexo", sexo);
                json.put("Descripcion", edtDescripcion.getText().toString());

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                Log.d("Response", response.body().string());
                Intent intent = new Intent(getApplicationContext(), MapsActivity3.class);
                Bundle datos = new Bundle();
                datos.putString(RegistrarNecesitado.PARAMETRO1, usuario);
                datos.putString(RegistrarNecesitado.PARAMETRO2, edtNombre.getText().toString());
                intent.putExtras(datos);
                startActivity(intent);

            } catch (IOException | JSONException e) {
                Log.d("Error", e.getMessage());
                Toast.makeText(getApplicationContext(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();

            }
        }
    }
    void ObtenerReferencia() {
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        txtDescripcion = (TextView) findViewById(R.id.txtDescripcion);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        rdbMasculino = (RadioButton) findViewById(R.id.rdbMasculino);
        rdbFemenino = (RadioButton) findViewById(R.id.rdbFemenino);
    }
}
