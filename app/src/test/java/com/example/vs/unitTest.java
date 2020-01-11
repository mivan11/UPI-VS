package com.example.vs;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class unitTest extends AppCompatActivity {
    String bat, temp, emis, vlaz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        bat = i.getExtras().getString("baterij");
        temp = i.getExtras().getString("temperatur");
        emis = i.getExtras().getString("emisij");
        vlaz = i.getExtras().getString("vlaznos");

        //Testiranje stringova
        System.out.println("Testiranje praznih stringova");
    }
        public void testPreconditions () {
            assertNotNull(bat);
            assertNotNull(temp);
            assertNotNull(emis);
            assertNotNull(vlaz);
            //assertEquals("baterija", bat());
        }

    }












