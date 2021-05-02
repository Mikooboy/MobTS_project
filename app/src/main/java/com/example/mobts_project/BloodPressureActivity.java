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
    TextView hPress;
    TextView lPress;
    TextView pulse;

    String information;
    int hPressure;
    int lPressure;
    int pulsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        Intent intent = getIntent();

        historyButton = findViewById(R.id.HistoryPressure);
        saveButton = findViewById(R.id.SavePressure);
        inputInfo = findViewById(R.id.InputInfo);
        hPress = findViewById(R.id.HighPressure);
        lPress = findViewById(R.id.LowPressure);
        pulse = findViewById(R.id.Pulse);

        inputInfo.setHint("Lisatiedot");
        hPress.setHint("Yläpaine");
        lPress.setHint("Alalapaine");
        pulse.setHint("Pulssi");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                information = inputInfo.getText().toString();
                hPressure = Integer.parseInt(hPress.getText().toString());
                lPressure = Integer.parseInt(lPress.getText().toString());
                pulsing = Integer.parseInt(pulse.getText().toString());

                Diaries.getInstance().getBloodPressures().add(new BloodPressure( hPressure , lPressure,  pulsing, information));
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