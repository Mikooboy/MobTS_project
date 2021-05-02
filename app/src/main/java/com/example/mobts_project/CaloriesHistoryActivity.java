package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CaloriesHistoryActivity extends AppCompatActivity {

    ListView caloriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_history);

        Intent intent = getIntent();

        caloriesList = findViewById(R.id.CaloriesList);

        caloriesList.setAdapter(new ArrayAdapter<Calories>(
                this,
                android.R.layout.simple_list_item_1,
                Diaries.getInstance().getCalories()
        ));
    }
}