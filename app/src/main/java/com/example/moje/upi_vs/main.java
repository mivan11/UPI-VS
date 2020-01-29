package com.example.moje.upi_vs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class main extends AppCompatActivity {
    Button postavke, podaci;

    private ImageView ivSettings, ivRefresh, ivData;
    private SwipeRefreshLayout srl;
    private TextView tvCO2Naslov,tvBatNaslov,tvNaslov,tvTempNaslov,tvVlazNaslov;
    private TextView tvCO2Val,tvBatVal,tvTempVal,tvVlazVal;
    private ImageView ivSlika;
    private String lang="";
    private String Unit="";

    //TextView tBaterija, tTemperatura, tEmisija, tVlaznost;
    String podatak="Baterija90%#Temperatura15°C#Emisija45#Vlaznost50%#";
    String zapis="";

    private myData data;
    private DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        tvBatNaslov=(TextView)findViewById(R.id.tvBatNaslov);
        tvCO2Naslov=(TextView)findViewById(R.id.tvCO2naslov);
        tvNaslov=(TextView)findViewById(R.id.tvNaslov);
        tvTempNaslov=(TextView)findViewById(R.id.tvTempNaslov);
        tvVlazNaslov=(TextView)findViewById(R.id.tvVlaznostNaslov);
        tvCO2Val=(TextView)findViewById(R.id.tvCO2Val);
        tvBatVal=(TextView)findViewById(R.id.tvBatVal);
        tvTempVal=(TextView)findViewById(R.id.tvTempVal);
        tvVlazVal=(TextView)findViewById(R.id.tvVlazVal);
        ivSlika=(ImageView)findViewById(R.id.ivSlika);

        final RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());


        if(getIntent().hasExtra("lang")){
            setjezik(getIntent().getStringExtra("lang"));
        }else{
            lang="Hrvatski";
        }
        if(getIntent().hasExtra("unit")){
            Unit =  getIntent().getStringExtra("unit");
        }else{
            Unit="°C";
        }
        setjezik(lang);

        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //String[] separated = currentDate.split("-");

        int bat = podatak.indexOf("B");
        int temp = podatak.indexOf("T");
        int emis = podatak.indexOf("E");
        int vlaz = podatak.indexOf("V");

        final String baterij=podatak.substring(bat+8,temp-2);
        final String temperatur =podatak.substring(temp+11, emis-3);
        final String emisij=podatak.substring(emis+7,vlaz-1);
        final String vlaznos =podatak.substring(vlaz+8, vlaz+10);

        ivData=(ImageView)findViewById(R.id.ivData);
        ivSettings = (ImageView) findViewById(R.id.ivSettings);
        ivData = (ImageView) findViewById(R.id.ivData);

        //Random rnd= new Random();
        //int tempo=rnd.nextInt(101);

        data=pomocna.generiraj();
        int tempo=Integer.parseInt(data.getTemperature());
        if(Unit.equals("Kelvin")){
            tempo+=274;
        }

        if(Unit.equals("Kelvin")){
            set_pic(tempo-274);
        }else{
            set_pic(tempo);
        }

        //data= new myData(baterij,String.valueOf(tempo)+Unit,emisij,vlaznos);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Mjerenja");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(currentDate)){
                    pomocna.set_data(currentDate,data,tvCO2Val,tvBatVal,tvTempVal,tvVlazVal);
                    //set_data(currentDate,data);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        srl= (SwipeRefreshLayout)findViewById(R.id.pullToRefresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Random rnd= new Random();
                //
                data=pomocna.generiraj();
                int tempo=Integer.parseInt(data.getTemperature());
                if(Unit.equals("Kelvin")){
                    tempo+=274;
                }
                data.setTemperature(String.valueOf(tempo)+Unit);
                set_data(currentDate,data);

                if(Unit.equals("Kelvin")){
                    set_pic(tempo-274);
                }else{
                    set_pic(tempo);
                }
                srl.setRefreshing(false);
            }
        });

        //zapis=("Baterija: "+baterij+"\nTemperatura: "+temperatur+"\nEmisija: "+ emisij+"\nVlažnost: "+vlaznos);
        ivData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), data.class);
                startActivity(intent);
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(rotate);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), settings.class);
                        intent.putExtra("lang", lang);
                        startActivity(intent);
                    }
                }, 500);
            }
        });
    }

    private void set_pic(int temp) {
        if (temp < 12) {
            ivSlika.setImageResource(R.drawable.rain);
        } else if (temp >= 12 & temp < 24) {
            ivSlika.setImageResource(R.drawable.partlycloudy);

        } else {
            ivSlika.setImageResource(R.drawable.sun17);
        }
    }

    private void set_data(final String currentDate,final myData data){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Mjerenja");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                rootRef.child(currentDate).child(currentTime).setValue(data);
                tvCO2Val.setText(data.getEmission()+"%");
                tvBatVal.setText(data.getBattery()+"%");
                tvTempVal.setText(data.getTemperature()+" ");
                tvVlazVal.setText(data.getMoisture()+"%");
                if (!(dataSnapshot.hasChild(currentDate))) {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setjezik(String lang){
        if(lang.equals("Engleski")){
            tvBatNaslov.setText("Battery");
            tvVlazNaslov.setText("Moist");
            tvCO2Naslov.setText("C02 Emission");
            tvTempNaslov.setText("Temperature");
            tvNaslov.setText("Weather Station");
        }
        else if(lang.equals("Hrvatski")){
            tvBatNaslov.setText("Baterija");
            tvCO2Naslov.setText("Emisija C02");
            tvVlazNaslov.setText("Vlažnost");
            tvTempNaslov.setText("Temperatura");
            tvNaslov.setText("Vremenska Stanica");
        }
        else if(lang.equals("Njemački")){
            tvBatNaslov.setText("Batterie");
            tvVlazNaslov.setText("Feucht");
            tvCO2Naslov.setText("C02 Emission");
            tvTempNaslov.setText("Temperatur");
            tvNaslov.setText("Wetterstation");
        }
    }

}
