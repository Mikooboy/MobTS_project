package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BloodPressureActivity extends AppCompatActivity {

    Button historyButton;
    Button saveButton;
    TextView inputInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        Intent intent = getIntent();

        historyButton = findViewById(R.id.HistoryButton);
        saveButton = findViewById(R.id.Savebutton);
        inputInfo = findViewById(R.id.InputInfo);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diaries.getInstance().getBloodPressures().add(new BloodPressure(1, 3,3,""));
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(BloodPressureActivity.this, PressureHistory.class);
                startActivity(nextActivity);
            }
        });

    }
}