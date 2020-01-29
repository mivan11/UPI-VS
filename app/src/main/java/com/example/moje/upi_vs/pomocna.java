package com.example.moje.upi_vs;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class pomocna {
    static void set_data(final String currentDate, final myData data, final TextView tvCO2Val, final TextView tvBatVal, final TextView tvTempVal,final TextView tvVlazVal){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Mjerenja");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
                rootRef.child(currentDate).child(currentTime).setValue(data);
                tvCO2Val.setText(data.getEmission()+"");
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


    static public void getdata(String date, final LinearLayout llscroll, final TextView tvtemp, final TextView tv2) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Mjerenja");
        rootRef.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    tvtemp.setText("");
                    llscroll.removeAllViews();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        String bat = ds.child("battery").getValue().toString();
                        String moi = ds.child("emission").getValue().toString();
                        String emi = ds.child("temperature").getValue().toString();
                        String temp = ds.child(("moisture")).getValue().toString();
                        tv2.setTypeface(Typeface.create("monospace", Typeface.NORMAL));
                        tv2.setText(key + "\n" + "Battery:" + bat + " Moisture:" + moi + " Emission:" + emi + " Temperature:" + temp + "\n" + "_________________________________" + "\n");
                        tv2.setTextSize(16);
                        //tv2.setTextColor(Color.parseColor("#0959e1"));
                        llscroll.addView(tv2);
                    }
                } else {
                    tvtemp.setTextColor(Color.parseColor("#a8202b"));
                    tvtemp.setText("No data");
                    llscroll.removeAllViews();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    static myData generiraj(){
        Random rnd = new Random();
        int temp= rnd.nextInt(30)+5;
        int bat= rnd.nextInt(101);
        int co= rnd.nextInt(500)+200;
        int moist=rnd.nextInt(80)+10;
        myData data=new myData(String.valueOf(bat),String.valueOf(temp),String.valueOf(co),String.valueOf(moist));
        return data;
    }
}
