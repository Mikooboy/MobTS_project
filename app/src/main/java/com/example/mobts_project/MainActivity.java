package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button medicine;
    Button diaries;
    Button activity;
    BloodPressure testi;
    String testi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicine = findViewById(R.id.MedicineButton);
        diaries = findViewById(R.id.DiariesButton);
        activity = findViewById(R.id.ActivityButton);

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextActivity = new Intent(MainActivity.this, MedicineActivity.class);
                startActivity(nextActivity);

            }
        });
        diaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextActivity = new Intent(MainActivity.this, DiariesActivity.class);
                startActivity(nextActivity);

            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextActivity = new Intent(MainActivity.this, ActivityActivity.class);
                startActivity(nextActivity);

            }
        });

        Diaries test = Diaries.getInstance();
        test.getBloodPressures().add(new BloodPressure(120,80,70,""));

        testi = test.getBloodPressures().get(0);
        testi2 = testi.toString();
        Log.d("TOIMIIKO", testi2);


    }
}