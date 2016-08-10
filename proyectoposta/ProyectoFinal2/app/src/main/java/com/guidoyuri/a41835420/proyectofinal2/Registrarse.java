package com.guidoyuri.a41835420.proyectofinal2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class  Registrarse extends AppCompatActivity {
    public static final String PARAMETRO1 = "com.guidoyuri.a41835420.proyectofinal2.PARAMETRO1";
    TextView txtUsuario;
    TextView txtContrasena;
    TextView txtConfi;
    TextView txtMail;
    EditText edtUsuario;
    EditText edtContrasena;
    EditText edtConfi;
    EditText edtMail;
    String idusu;
    int errores = 0;
    Button btnEnviar;
    ArrayList<Usuario> usuarios;
    ListView listVW;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        ObtenerReferencia();
        usuarios = new ArrayList<Usuario>();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Enviar();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }
    ArrayAdapter<Usuario> adapter;
    boolean pause = true;
    private void Enviar() {
        String usuario = edtUsuario.getText().toString();
        usuarios= new ArrayList<Usuario>();
        String URLSELECT="http://guidoyuri.hol.es/bd/TraerUsuarios.php?NombreUsuario="+usuario;

        new UsuariosTask().execute(URLSELECT);




    }

    private class UsuariosTask extends AsyncTask<String, Void, ArrayList<Usuario>> {
        private OkHttpClient client = new OkHttpClient();

        protected void onPostExecute(ArrayList<Usuario> usuariosResult) {
            super.onPostExecute(usuariosResult);
            if (!usuariosResult.isEmpty()) {
                usuarios = usuariosResult;
            }
            String usuario = edtUsuario.getText().toString();
            String contra = edtContrasena.getText().toString();
            String confi = edtConfi.getText().toString();
            String mail = edtMail.getText().toString();
             if (contra.length() < 8) {
            errores = 1;
            txtUsuario.setTextColor(Color.BLACK);
            txtConfi.setTextColor(Color.BLACK);
            txtMail.setTextColor(Color.BLACK);
            txtContrasena.setTextColor(Color.RED);
        }
        if (!contra.equals(confi)) {
            errores = 1;
            txtUsuario.setTextColor(Color.BLACK);
            txtMail.setTextColor(Color.BLACK);
            txtContrasena.setTextColor(Color.RED);
            txtConfi.setTextColor(Color.RED);
        }
        if (ValidarMail(mail) == false) {
            errores = 1;
            txtUsuario.setTextColor(Color.BLACK);
            txtContrasena.setTextColor(Color.BLACK);
            txtConfi.setTextColor(Color.BLACK);
            txtMail.setTextColor(Color.BLACK);
            txtMail.setTextColor(Color.RED);

        }
            if (usuario == "") {
                errores = 1;
                txtUsuario.setTextColor(Color.RED);
            }
            if (contra == "") {
                errores = 1;
                txtContrasena.setTextColor(Color.RED);
            }
            if (confi == "") {
                errores = 1;
                txtConfi.setTextColor(Color.RED);
            }
            if (mail == "") {
                errores = 1;
                txtMail.setTextColor(Color.RED);
            }

       /* try {
            Thread.sleep(500);
        }
        catch(InterruptedException e){


        }*/

            if(usuarios.size() !=0){
                errores = 1;
                Toast.makeText(getApplicationContext(), "Ese ya existe", Toast.LENGTH_SHORT).show();
            }
            if (errores == 0) {
                try {
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://guidoyuri.hol.es/bd/AgregarUsuario.php";
                    JSONObject json = new JSONObject();
                    json.put("NombreUsuario", usuario);
                    json.put("Contrasena", contra);
                    json.put("Email", mail);

                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();
                    Log.d("Response", response.body().string());
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    Bundle datos = new Bundle();
                    datos.putString(Registrarse.PARAMETRO1, usuario);
                    intent.putExtras(datos);
                    startActivity(intent);

                } catch (IOException | JSONException e) {
                    Log.d("Error", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();

                }


            }
            else
            {
                Toast.makeText(getApplicationContext(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();
                errores=0;
            }
        }

        @Override
        protected ArrayList<Usuario> doInBackground(String... params) {

                String url = params[0];

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    return parsearResultado(response.body().string());

                } catch (IOException | JSONException e) {
                    Log.d("Error", e.getMessage());
                    return new ArrayList<Usuario>();
                }

        }

        ArrayList<Usuario> parsearResultado(String JSONstr) throws JSONException {

            JSONArray jsonUsuarios = new JSONArray(JSONstr);
            for (int i = 0; i < jsonUsuarios.length(); i++) {
                JSONObject jsonResultado = jsonUsuarios.getJSONObject(i);

                String idUsuario = jsonResultado.getString("idUsuario");
                Usuario e = new Usuario(idUsuario);
                usuarios.add(e);
            }
            return usuarios;
        }
    }




    boolean ValidarMail(String mail) {
        if (mail.contains("@") && mail.contains(".")) {
            return true;
        } else {
            return false;
        }
    }

    void ObtenerReferencia() {
        txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        txtContrasena = (TextView) findViewById(R.id.txtContrasena);
        txtConfi = (TextView) findViewById(R.id.txtConfi);
        txtMail = (TextView) findViewById(R.id.txtMail);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtContrasena = (EditText) findViewById(R.id.edtContra);
        edtConfi = (EditText) findViewById(R.id.edtConfi);
        edtMail = (EditText) findViewById(R.id.edtMail);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registrarse Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.guidoyuri.a41835420.proyectofinal2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registrarse Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.guidoyuri.a41835420.proyectofinal2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }
}
