package com.example.moje.upi_vs;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class data extends AppCompatActivity {
    private CalendarView cvCalendar;
    private String date;
    private DatabaseReference reff;
    private LinearLayout llscroll;
    private TextView tvtemp;
    private ImageView ivDes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        //ivDes=(ImageView)findViewById(R.id.ivDes);
        cvCalendar = (CalendarView) findViewById(R.id.cvCalendar);
        llscroll = (LinearLayout) this.findViewById(R.id.llScroll);
        tvtemp = (TextView) findViewById(R.id.tvtemp);

        cvCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Day, Month, Year;
                month += 1;
                if (dayOfMonth < 10) {
                    Day = "0" + dayOfMonth;
                } else {
                    Day = String.valueOf(dayOfMonth);
                }
                Year = String.valueOf(year);
                if (month < 10) {
                    Month = "0" + month;
                } else {
                    Month = String.valueOf(dayOfMonth);
                }
                date = Day + "-" + Month + "-" + Year;
                TextView tv2 = new TextView(data.this);
                //pomocna.getdata(date , llscroll,tvtemp,tv2);
                getdata(date);
            }
        });

    }

    public void getdata(String date) {
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
                        TextView tv1 = new TextView(data.this);

                        tv1.setTypeface(Typeface.create("monospace", Typeface.NORMAL));
                        tv1.setText(key + "\n" + "Battery:" + bat + " Moisture:" + moi + " Emission:" + emi + " Temperature:" + temp + "\n" + "_________________________________" + "\n");

                        tv1.setTextSize(16);
                        tv1.setTextColor(Color.parseColor("#0959e1"));
                        llscroll.addView(tv1);
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


}