package com.example.mobts_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class BloodSugarActivity extends AppCompatActivity {

    TextView inputInfo;
    TextView bloodSugarNumber;
    Button historyButton;
    Button saveButton;

    String information;
    int bloodSugars;
    Intent intent;

    SharedPreferences.Editor prefEditor ;
    SharedPreferences prefGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);

        intent = getIntent();

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEditor = prefGet.edit();

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

                Diaries.getInstance().getBloodSugars().add(new BloodSugar( bloodSugars , information , LocalDate.now().format(DateTimeFormatter.ofPattern("E, dd.MM.yyyy", new Locale("fi")))));

                Gson gson = new Gson();
                String json = gson.toJson(Diaries.getInstance().getBloodSugars());
                Log.d("JSOn", json);
                prefEditor.putString("sokerilista", json);
                prefEditor.commit();

            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = prefGet.getString("sokerilista", null);
                Type type = new TypeToken<ArrayList<BloodSugar>>() {}.getType();
                Diaries.getInstance().setBloodSugars(gson.fromJson(json, type));

                if (Diaries.getInstance().getBloodSugars() == null) {
                    Diaries.getInstance().setBloodSugars(new ArrayList<BloodSugar>());
                }

                Intent nextActivity = new Intent(BloodSugarActivity.this, SugarsHistoryActivity.class);
                startActivity(nextActivity);
            }
        });
    }
}