package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PressureHistory extends AppCompatActivity {

    ListView pressureList;

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
    }
}