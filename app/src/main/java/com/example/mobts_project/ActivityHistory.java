package com.example.mobts_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * History of Aktiivisuus (activity). Allows to see saved steps and dates
 * Starts ActivityHistoryDetails
 * @author Roope Jantunen
 */
public class ActivityHistory extends AppCompatActivity {
    public static final String DATE = "com.example.MobTS_project.STEPDATE";
    public static final String STEPS = "com.example.exercise_6.STEPS";
    public static final String INDEX = "com.example.exercise_6.STEPINDEX";
    SharedPreferences prefGet;
    SharedPreferences.Editor prefEditor;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEditor = prefGet.edit();

        listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<Steps>(
                this,
                android.R.layout.simple_list_item_1,
                StepData.getInstance().getStepsList()
        ));

        /*
        Starts and gives needed info to ActivityHistoryDetails
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(ActivityHistory.this, ActivityHistoryDetails.class);
                nextActivity.putExtra(DATE, StepData.getInstance().getStepsList().get(i).getDate());
                nextActivity.putExtra(STEPS, StepData.getInstance().getStepsList().get(i).getSteps());
                nextActivity.putExtra(INDEX, i);
                startActivity(nextActivity);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Same as LoadList() in ActivityActivity
        //Get json string and makes it a ArrayList
        Gson gson = new Gson();
        String json = prefGet.getString("StepList", null);
        Type type = new TypeToken<ArrayList<Steps>>() {}.getType();
        StepData.getInstance().setStepsList(gson.fromJson(json, type));

        if (StepData.getInstance().getStepsList() == null) {
            StepData.getInstance().setStepsList(new ArrayList<Steps>());
        }
    }
}