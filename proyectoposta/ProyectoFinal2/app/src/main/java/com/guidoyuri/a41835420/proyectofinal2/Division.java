package com.guidoyuri.a41835420.proyectofinal2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Division extends AppCompatActivity {
    public static final String PARAMETRO3 = "com.guidoyuri.a41835420.proyectofinal2.PARAMETRO3";
    public static final String PARAMETRO4 = "com.guidoyuri.a41835420.proyectofinal2.PARAMETRO4";
    String usuario;
    String idusu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);
        Intent elintent = getIntent();
        Bundle datos=elintent.getExtras();
        usuario=datos.getString(MapsActivity.PARAMETRO2);
        TextView txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        Button btnDonar  = (Button) findViewById(R.id.btnDonar1);
        Button btnIntermediario  = (Button) findViewById(R.id.btnIntermediario);
        txtUsuario.setText(usuario);
        btnDonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Donante();
            }
        });
        btnIntermediario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intermediario();
            }
        });
    }
    private void Donante()
    {
        Intent intent = new Intent(this, Donacion.class);
        Bundle datos = new Bundle();
        datos.putString(Division.PARAMETRO3, usuario);
        intent.putExtras(datos);
        startActivity(intent);
    }
    private void Intermediario()
    {
        Intent intent = new Intent(this, RegistrarNecesitado.class);
        Bundle datos = new Bundle();
        datos.putString(Division.PARAMETRO4, usuario);
        intent.putExtras(datos);
        startActivity(intent);
    }
}
