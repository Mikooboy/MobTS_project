package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BloodPressureActivity extends AppCompatActivity {

    Button historyButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        Intent intent = getIntent();

        historyButton = findViewById(R.id.HistoryButton);
        saveButton = findViewById(R.id.Savebutton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diaries.getInstance().getBloodPressures().add(new BloodPressure());
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(BloodPressureActivity.this, PressureHistory.class)
            }
        });

    }
}