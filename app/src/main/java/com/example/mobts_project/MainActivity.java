package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

BloodPressure testi;
String testi2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TOIMIIKO?

        //Kyllä toimii t:Miko

        Diaries test = Diaries.getInstance();
        test.getBloodPressures().add(new BloodPressure(120,80,70));

        testi = test.getBloodPressures().get(0);
        testi2 = testi.toString();
        Log.d("TOIMIIKO", testi2);

    }
}