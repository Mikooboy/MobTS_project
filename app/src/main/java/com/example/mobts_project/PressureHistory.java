package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PressureHistory extends AppCompatActivity {

    public static final String DATE = "com.example.MobTS_project.MESSAGE_pressure1";
    public static final String HPRESS = "com.example.exercise_6.MESSAGE_pressure2";
    public static final String LPRESS = "com.example.exercise_6.MESSAGE_pressure3";
    public static final String PULSE = "com.example.exercise_6.MESSAGE_pressure4";
    public static final String INFO = "com.example.exercise_6.MESSAGE_pressure5";
    public static final String INDEX = "com.example.exercise_6.MESSAGE_pressure6";

    ListView pressureList;

    SharedPreferences prefGet;
    SharedPreferences.Editor prefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_history);

        pressureList = findViewById(R.id.PressureList);

        pressureList.setAdapter(new ArrayAdapter<BloodPressure>(
                this,
                android.R.layout.simple_list_item_1,
                Diaries.getInstance().getBloodPressures()
                ));

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEdit = prefGet.edit();

        pressureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("TAG", "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(PressureHistory.this , PressureHistoryDetails.class);
                nextActivity.putExtra(DATE, Diaries.getInstance().getBloodPressures().get(i).getDate());
                nextActivity.putExtra(HPRESS, Diaries.getInstance().getBloodPressures().get(i).getHighPress());
                nextActivity.putExtra(LPRESS, Diaries.getInstance().getBloodPressures().get(i).getLowPress());
                nextActivity.putExtra(PULSE, Diaries.getInstance().getBloodPressures().get(i).getPulse());
                nextActivity.putExtra(INFO, Diaries.getInstance().getBloodPressures().get(i).getInfo());
                nextActivity.putExtra(INDEX, i);
                startActivity(nextActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gson gson = new Gson();
        String json = prefGet.getString("painelista", null);
        Type type = new TypeToken<ArrayList<BloodPressure>>() {}.getType();
        Diaries.getInstance().setBloodPressures(gson.fromJson(json, type));

        if (Diaries.getInstance().getBloodPressures() == null) {
            Diaries.getInstance().setBloodPressures(new ArrayList<BloodPressure>());
        }
    }
}