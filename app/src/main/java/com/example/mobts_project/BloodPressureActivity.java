package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

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

    SharedPreferences.Editor prefEditor ;
    SharedPreferences prefGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        Intent intent = getIntent();

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEditor = prefGet.edit();

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

                Diaries.getInstance().getBloodPressures().add(new BloodPressure( hPressure , lPressure,  pulsing, information ,  LocalDate.now().format(DateTimeFormatter.ofPattern("E, dd.MM.yyyy", new Locale("fi")))));

                Gson gson = new Gson();
                String json = gson.toJson(Diaries.getInstance().getBloodPressures());
                Log.d("JSOn", json);
                prefEditor.putString("painelista", json);
                prefEditor.commit();
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = prefGet.getString("painelista", null);
                Type type = new TypeToken<ArrayList<BloodPressure>>() {}.getType();
                Diaries.getInstance().setBloodPressures(gson.fromJson(json, type));

                if (Diaries.getInstance().getBloodPressures() == null) {
                    Diaries.getInstance().setBloodPressures(new ArrayList<BloodPressure>());
                }

                Intent nextActivity = new Intent(BloodPressureActivity.this, PressureHistory.class);
                startActivity(nextActivity);
            }
        });

    }
}