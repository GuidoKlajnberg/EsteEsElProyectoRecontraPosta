package com.guidoyuri.a41835420.proyectofinal2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListViewMultipleChoiceExample extends Activity {
    private ListView lView;
    ArrayList<String> lista;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_view_multiple_choice_example);
        Intent elintent = getIntent();
        Bundle datos=elintent.getExtras();
        lista=datos.getStringArrayList(Donacion.PARAMETRO3);
        lView = (ListView) findViewById(R.id.ListView01);
//	Set option as Multiple Choice. So that user can able to select more the one option from list
        lView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, lista));
        lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
}
