package com.example.mobts_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BloodSugarActivity extends AppCompatActivity {

    TextView inputInfo;
    TextView bloodSugarNumber;
    Button historyButton;
    Button saveButton;

    String information;
    int bloodSugars;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);

        intent = getIntent();

        inputInfo = findViewById(R.id.SugarInfoText);
        bloodSugarNumber = findViewById(R.id.SugarNumber);
        historyButton = findViewById(R.id.HistorySugar);
        saveButton = findViewById(R.id.SaveSugar);

        inputInfo.setHint("Lisätiedot");
        bloodSugarNumber.setHint("Verensokeriarvot");

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