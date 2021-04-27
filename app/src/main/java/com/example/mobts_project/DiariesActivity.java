package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiariesActivity extends AppCompatActivity {

    Button bloodPressureButton;
    Button bloodSugarsButton;
    Button caloriesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);

        bloodPressureButton = findViewById(R.id.PressureButton);
        bloodSugarsButton = findViewById(R.id.SugarsButton);
        caloriesButton = findViewById(R.id.CaloriesButton);

        bloodPressureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(DiariesActivity.this, BloodPressureActivity.class);
                startActivity(nextActivity);
            }
        });
        bloodSugarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(DiariesActivity.this, BloodSugarActivity.class);
                startActivity(nextActivity);
            }
        });
        caloriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(DiariesActivity.this, CaloriesActivity.class);
                startActivity(nextActivity);
            }
        });

    }
}