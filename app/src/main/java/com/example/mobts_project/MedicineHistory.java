package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MedicineHistory extends AppCompatActivity {
    public static final String EXTRA = "com.example.MobTS_project.EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_history);
        

        ListView lv = findViewById(R.id.datesList);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, loadDays());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(MedicineHistory.this, MedicineHistoryDay.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);
            }
        });
    }

    public ArrayList<String> loadDays() {
        SharedPreferences prefGet = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonDates = prefGet.getString("dates", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> dates = gson.fromJson(jsonDates, type);

        if (dates == null) {
            dates = new ArrayList<String>();
        }

        return dates;
    }
}