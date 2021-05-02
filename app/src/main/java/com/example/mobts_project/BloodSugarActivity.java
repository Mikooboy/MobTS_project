package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BloodSugarActivity extends AppCompatActivity {

    TextView inputInfo;
    TextView bloodSugarNumber;
    Button historyButton;
    Button saveButton;

    String information;
    int bloodSugars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);

        Intent intent = getIntent();

        inputInfo = findViewById(R.id.SugarInfoText);
        bloodSugarNumber = findViewById(R.id.SugarNumber);
        historyButton = findViewById(R.id.HistorySugar);
        saveButton = findViewById(R.id.SaveSugar);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                information =  inputInfo.getText().toString();
                bloodSugars = Integer.parseInt(bloodSugarNumber.getText().toString());

                Diaries.getInstance().getBloodSugars().add(new BloodSugar( bloodSugars , information));
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextActivity = new Intent(BloodSugarActivity.this, SugarsHistoryActivity.class);
                startActivity(nextActivity);
            }
        });
    }
}