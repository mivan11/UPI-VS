package com.example.moje.upi_vs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    private Spinner spinnerunit,spinnerlanguage;
    private TextView tvUnit,tvLang;
    private Button btnUpdate;
    private String langu="";
    private String Unit="°C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        langu = getIntent().getStringExtra("lang");
        if(langu.isEmpty()){
            langu="Hrvatski";
        }


        tvUnit=(TextView)findViewById(R.id.tvUnit);
        tvLang=(TextView)findViewById(R.id.tvLang);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);

        setjezik(langu);


        spinnerunit = (Spinner)findViewById(R.id.unit);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.unit,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerunit.setAdapter(staticAdapter);

        spinnerlanguage = (Spinner)findViewById(R.id.language);

        ArrayAdapter<CharSequence> staticAdapter2 = ArrayAdapter
                .createFromResource(this, R.array.language,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlanguage.setAdapter(staticAdapter2);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lang = spinnerlanguage.getSelectedItem().toString();
                String unit = spinnerunit.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), main.class);
                intent.putExtra("unit", unit);

                intent.putExtra("lang", lang);
                startActivity(intent);
            }
        });

    }


    public void setjezik(String lang){
        tvUnit.setText(lang);
        if(lang.equals("Engleski")){
            tvUnit.setText("Unit");
            tvLang.setText("Language");
            btnUpdate.setText("Update");

        }
        if(lang.equals("Hrvatski")){
            tvUnit.setText("Jedinica");
            tvLang.setText("Jezik");
            btnUpdate.setText("Ažuriraj");

        }
        if(lang.equals("Njemački")){
            tvUnit.setText("Einheit");
            tvLang.setText("Sprache");
            btnUpdate.setText("Aktualisieren");

        }


    }

}
