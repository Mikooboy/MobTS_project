package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CaloriesActivity extends AppCompatActivity {

    Button historyButton;
    Button saveButton;
    TextView calorieInput;

    int calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);

        Intent intent = getIntent();

        historyButton = findViewById(R.id.HistoryCalories);
        saveButton = findViewById(R.id.SaveCalories);
        calorieInput = findViewById(R.id.CalorieInput);

        calorieInput.setHint("Kalorit");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calories = Integer.parseInt(calorieInput.getText().toString());
                Diaries.getInstance().getCalories().add(new Calories());
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(CaloriesActivity.this, CaloriesHistoryActivity.class);
                startActivity(nextActivity);
            }
        });

    }
}