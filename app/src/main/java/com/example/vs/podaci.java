package com.example.vs;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class podaci extends AppCompatActivity {
    TextView prikaz;
    String podatak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podaci);
        Intent i = getIntent();
        podatak = i.getExtras().getString("zapis");
        prikaz = (TextView)findViewById(R.id.textViewPodaci1);
        prikaz.setText(podatak);
    }

}