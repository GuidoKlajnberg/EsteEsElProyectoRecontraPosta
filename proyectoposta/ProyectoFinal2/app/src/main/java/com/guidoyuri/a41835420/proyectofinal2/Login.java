package com.guidoyuri.a41835420.proyectofinal2;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    public static final String PARAMETRO1 = "com.guidoyuri.a41835420.proyectofinal2.PARAMETRO1";
    TextView txtUsuario;
    TextView txtContrasena;
    EditText edtUsuario;
    EditText edtContrasena;
    Button btnIngresar;
    Button btnRegistrarse;
    String usuario;
    String contrasena;
    ArrayList<String>contrasenas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        txtContrasena = (TextView) findViewById(R.id.txtContrasena);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingresar();
            }
        });
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarse();
            }
        });
    }
    private void registrarse()
    {
        Intent intent = new Intent(getApplicationContext(), Registrarse.class);
        startActivity(intent);
    }
    private void Ingresar() {



            if (edtUsuario.getText().toString() == "" || edtContrasena.getText().toString() == "")
            {
                Toast.makeText(getApplicationContext(), "No pueden quedar datos vacios", Toast.LENGTH_SHORT).show();
                if (edtUsuario.getText().toString() == "")
                {
                    txtUsuario.setTextColor(Color.RED);
                }
                if (edtContrasena.getText().toString() == "")
                {
                    txtContrasena.setTextColor(Color.RED);
                }

            } else {
                usuario = edtUsuario.getText().toString();
                contrasena = edtContrasena.getText().toString();
                String URLSELECT = "http://guidoyuri.hol.es/bd/Login.php?NombreUsuario=" + usuario;

                new UsuariosTask().execute(URLSELECT);
            }
        }

    private class UsuariosTask extends AsyncTask<String, Void, ArrayList<String>> {
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPostExecute(ArrayList<String> contras) {
            contrasenas=new ArrayList<>();
            super.onPostExecute(contras);
            contrasenas=contras;
            if (contrasenas.get(0)==contrasena)
            {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle datos = new Bundle();
                datos.putString(Login.PARAMETRO1, usuario);
                intent.putExtras(datos);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "El usuario o la contrasena no son correctos", Toast.LENGTH_SHORT).show();
            }

            }


        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String url = params[0];

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return parsearResultado(response.body().string());
            } catch (IOException | JSONException e) {
                Log.d("Error", e.getMessage());
                return new ArrayList<String>();
            }
        }

        ArrayList<String> parsearResultado(String JSONstr) throws JSONException {
            ArrayList<String> contrasenas = new ArrayList<>();
            JSONArray jsonContrasenas = new JSONArray(JSONstr);
            for (int i = 0; i < jsonContrasenas.length(); i++) {
                JSONObject jsonResultado = jsonContrasenas.getJSONObject(i);

                String contra = jsonResultado.getString("Contrasena");

                String e = new String(contra);
                contrasenas.add(e);
            }
            return contrasenas;
        }

    }

}
