package com.example.moje.upi_vs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class main extends AppCompatActivity {
    Button postavke, podaci;
    TextView tBaterija, tTemperatura, tEmisija, tVlaznost;
    String podatak="Baterija90%#Temperatura15°C#Emisija45#Vlaznost50%#";
    String zapis="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postavke=(Button)findViewById(R.id.postavke);
        podaci=(Button)findViewById(R.id.podaci);
        tBaterija=(TextView)findViewById(R.id.baterija);
        tTemperatura=(TextView)findViewById(R.id.temperatura);
        tEmisija=(TextView)findViewById(R.id.emisija);
        tVlaznost=(TextView)findViewById(R.id.vlaznost);

        int bat = podatak.indexOf("B");
        int temp = podatak.indexOf("T");
        int emis = podatak.indexOf("E");
        int vlaz = podatak.indexOf("V");

        String baterij=podatak.substring(bat+8,temp-1);
        String temperatur =podatak.substring(temp+11, emis-1);
        String emisij=podatak.substring(emis+7,vlaz-1);
        String vlaznos =podatak.substring(vlaz+8, vlaz+10);

        tBaterija.setText(baterij);
        tTemperatura.setText(temperatur);
        tEmisija.setText(emisij);
        tVlaznost.setText(vlaznos);

        zapis=("Baterija: "+baterij+"\nTemperatura: "+temperatur+"\nEmisija: "+ emisij+"\nVlažnost: "+vlaznos);

        podaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main.this, podaci.class);
                i.putExtra("zapis", zapis);
                startActivity(i);
            }
        });

    }
}
