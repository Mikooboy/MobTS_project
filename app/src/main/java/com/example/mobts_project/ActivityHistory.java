package com.example.mobts_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityHistory extends AppCompatActivity {
    public static final String DATE = "com.example.exercise_6.MESSAGE";
    public static final String STEPS = "com.example.exercise_6.MESSAGE2";
    public static final String INDEX = "com.example.exercise_6.MESSAGE3";


    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<Steps>(
                this,
                android.R.layout.simple_list_item_1,
                StepData.getInstance().getStepsList()
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(ActivityHistory.this, ActivityHistoryDetails.class);
                nextActivity.putExtra(DATE, StepData.getInstance().getStepsList().get(i).date);
                nextActivity.putExtra(STEPS, StepData.getInstance().getStepsList().get(i).getSteps());
                nextActivity.putExtra(INDEX, i);
                startActivity(nextActivity);
            }
        });

    }
}