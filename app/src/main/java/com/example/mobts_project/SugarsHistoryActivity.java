package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SugarsHistoryActivity extends AppCompatActivity {

    ListView sugarsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugars_history);

        sugarsList = findViewById(R.id.SugarsList);

        sugarsList.setAdapter(new ArrayAdapter<BloodSugar>(
                this,
                android.R.layout.simple_list_item_1,
                Diaries.getInstance().getBloodSugars()
        ));
    }
}