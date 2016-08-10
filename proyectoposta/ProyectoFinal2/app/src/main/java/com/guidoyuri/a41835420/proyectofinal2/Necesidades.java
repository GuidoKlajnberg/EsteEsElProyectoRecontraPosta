package com.guidoyuri.a41835420.proyectofinal2;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Necesidades extends AppCompatActivity {
    public static final String PARAMETRO3 = "com.guidoyuri.a41835420.proyectofinal2.PARAMETRO3";
    String usuario;
    String necesitado;
    ListView listCosas;
    ListView listHabilidades;

    ArrayList<String> lista;
    ArrayList<String> listadonar;
    ArrayList<String> listadonarh;
    ArrayList<String> listah;
    ArrayList<CosaNecesitada>lv_items;
    Button btnEnviar;
    SparseBooleanArray seleccionados;
    ArrayList<CosaNecesitada> cosasnecesitadas;
    ArrayList<Habilidad> habilidades;
    ArrayList<Usuario> usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_necesidades);
        Intent elintent = getIntent();
        Bundle datos=elintent.getExtras();
        usuario=datos.getString(MapsActivity3.PARAMETRO1);
        necesitado=datos.getString(MapsActivity3.PARAMETRO2);
        TextView txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        txtUsuario.setText(usuario);
        listCosas = (ListView) findViewById(R.id.listCosas);
        listHabilidades = (ListView) findViewById(R.id.listHabilidades);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        String url = "http://guidoyuri.hol.es/bd/TraerCosasNecesitadas.php";
        String url1="http://guidoyuri.hol.es/bd/TraerHabilidades.php";
        usuarios= new ArrayList<Usuario>();
        new EventosTask().execute(url);
        new EventosTask1().execute(url1);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Enviar();
            }
        });



//	Set option as Multiple Choice. So that user can able to select more the one option from list


    }
    private void Enviar()
    {


        ArrayList<String> checked = new ArrayList<String>();
        SparseBooleanArray sparseBooleanArray = listCosas.getCheckedItemPositions();
        SparseBooleanArray sparseBooleanArray1 = listHabilidades.getCheckedItemPositions();
        int estado=0;
        if (sparseBooleanArray.size()==0&&sparseBooleanArray1.size()==0)
        {
            Toast.makeText(getApplicationContext(), "Debe ingresar algun dato", Toast.LENGTH_LONG).show();
        }
        else {
            if (sparseBooleanArray.size()!=0) {
                int cntChoice = listCosas.getCount();
                for (int i = 0; i < cntChoice; i++) {

                    if (sparseBooleanArray.get(i) == true) {
                        checked.add(String.valueOf(i + 1));
                    }

                }
                for (int i = 0; i < checked.size(); i++) {
                    String aa = checked.get(i);


                    try {
                        if (android.os.Build.VERSION.SDK_INT > 9) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                        }
                        OkHttpClient client = new OkHttpClient();
                        String url = "http://guidoyuri.hol.es/bd/AgregarNecesitadoCosaNecesitada.php";
                        JSONObject json = new JSONObject();
                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => " + c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = df.format(c.getTime());
                        json.put("Necesitado_Nombre", necesitado);
                        json.put("CosasNecesitadas_idCosaNecesitada", aa);
                        json.put("Fecha", formattedDate);

                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .build();

                        Response response = client.newCall(request).execute();
                        Log.d("Response", response.body().string());
                        estado=1;
                        Toast.makeText(getApplicationContext(), "Hasta acá hemos llegado con el sistema. En un tiempo habrá más progreso. Disculpe las molestias", Toast.LENGTH_LONG).show();


                    } catch (IOException | JSONException e) {
                        Log.d("Error", e.getMessage());
                        Toast.makeText(getApplicationContext(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            if (sparseBooleanArray.size()!=0) {
                int cntChoice1 = listHabilidades.getCount();

                ArrayList<String> checked1 = new ArrayList<String>();


                for (int i = 0; i < cntChoice1; i++) {

                    if (sparseBooleanArray1.get(i) == true) {
                        checked1.add(String.valueOf(i + 1));
                    }

                }
                for (int i = 0; i < checked1.size(); i++) {
                    String aaa = checked1.get(i);

                    try {
                        if (android.os.Build.VERSION.SDK_INT > 9) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                        }
                        OkHttpClient client = new OkHttpClient();
                        String url = "http://guidoyuri.hol.es/bd/AgregarNecesitadoHabilidades.php";
                        JSONObject json = new JSONObject();
                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => " + c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = df.format(c.getTime());
                        json.put("Habilidades_idHabilidad", aaa);
                        json.put("Necesitado_Nombre", necesitado);
                        json.put("Fecha", formattedDate);

                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .build();

                        Response response = client.newCall(request).execute();
                        Log.d("Response", response.body().string());
                        if(estado==0)
                        {
                            Toast.makeText(getApplicationContext(), "Hasta acá hemos llegado con el sistema. En un tiempo habrá más progreso. Disculpe las molestias", Toast.LENGTH_LONG).show();
                        }

                    } catch (IOException | JSONException e) {
                        Log.d("Error", e.getMessage());
                        Toast.makeText(getApplicationContext(), "Ingrese correctamente los datos", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }

    }



    private class EventosTask extends AsyncTask<String, Void, ArrayList<CosaNecesitada>> {
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPostExecute(ArrayList<CosaNecesitada> cosasnecesitadasResult) {
            lista=new ArrayList<String>();
            super.onPostExecute(cosasnecesitadasResult);
            cosasnecesitadas=cosasnecesitadasResult;
            if (!cosasnecesitadasResult.isEmpty()) {
                for (CosaNecesitada cos:cosasnecesitadasResult)
                {
                    String art = cos.getNombre();
                    lista.add(art.toString());

                }
                listCosas.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, lista));
                listCosas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listCosas.setBackgroundColor(Color.BLACK);



            }
        }

        @Override
        protected ArrayList<CosaNecesitada> doInBackground(String... params) {
            String url = params[0];

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return parsearResultado(response.body().string());
            } catch (IOException | JSONException e) {
                Log.d("Error", e.getMessage());
                return new ArrayList<CosaNecesitada>();
            }
        }

        ArrayList<CosaNecesitada> parsearResultado(String JSONstr) throws JSONException {
            ArrayList<CosaNecesitada> cosasnecesitadas = new ArrayList<>();
            JSONArray jsonCosasNecesitadas = new JSONArray(JSONstr);
            for (int i = 0; i < jsonCosasNecesitadas.length(); i++) {
                JSONObject jsonResultado = jsonCosasNecesitadas.getJSONObject(i);

                String nombre = jsonResultado.getString("Nombre");

                CosaNecesitada e = new CosaNecesitada(nombre);
                cosasnecesitadas.add(e);
            }
            return cosasnecesitadas;
        }

    }





    private class EventosTask1 extends AsyncTask<String, Void, ArrayList<Habilidad>> {
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPostExecute(ArrayList<Habilidad> habilidadesResult) {
            listah = new ArrayList<String>();
            super.onPostExecute(habilidadesResult);
            habilidades = habilidadesResult;
            if (!habilidadesResult.isEmpty()) {
                for (Habilidad ha : habilidadesResult) {
                    String art = ha.getNombre();
                    listah.add(art.toString());

                }
                listHabilidades.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, listah));
                listHabilidades.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listHabilidades.setBackgroundColor(Color.BLACK);

            }
        }

        @Override
        protected ArrayList<Habilidad> doInBackground(String... params) {
            String url = params[0];

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return parsearResultado(response.body().string());
            } catch (IOException | JSONException e) {
                Log.d("Error", e.getMessage());
                return new ArrayList<Habilidad>();
            }
        }

        ArrayList<Habilidad> parsearResultado(String JSONstr) throws JSONException {
            ArrayList<Habilidad> habilidades = new ArrayList<>();
            JSONArray jsonHabilidades = new JSONArray(JSONstr);
            for (int i = 0; i < jsonHabilidades.length(); i++) {
                JSONObject jsonResultado = jsonHabilidades.getJSONObject(i);

                String nombre = jsonResultado.getString("Nombre");

                Habilidad e = new Habilidad(nombre);
                habilidades.add(e);
            }
            return habilidades;
        }

    }





}