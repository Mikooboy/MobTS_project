package com.example.mobts_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobts_project.medicine.MedicineActivity;

/**
 * Main activity works as a launcher for Diaries, Medicine and Activity activities
 * @author Julius Saukonoja
 */

public class MainActivity extends AppCompatActivity {

    Button medicine;
    Button diaries;
    Button activity;

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

    }
}