package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MedicineHistoryDay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_history_day);

        Intent intent = getIntent();
        int item = intent.getIntExtra(MedicineHistory.EXTRA, 0);

        TextView dateText = findViewById(R.id.dateText);

        String date = loadDays().get(item);
        dateText.setText(date);

        ArrayList<String> names = loadDaysNames(date);

        ListView lv = findViewById(R.id.medicationList);

        if (names.size() > 0) {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, names) {
                @Override
                public int getViewTypeCount() {

                    return getCount();
                }

                @Override
                public int getItemViewType(int position) {

                    return position;
                }
            };

            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        } else {
            lv.setAdapter(null);
        }

        HashMap<String, Boolean> checked = loadDaysSelections(date);
            for (int i = 0; i < checked.size(); i++) {
            try {
                Log.d("test", "updateReminders: " + checked);
                Boolean check = checked.get(names.get(i));
                lv.setItemChecked(i, check);
            } catch(Exception e) {
                //  Block of code to handle errors
            }
        }
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

    public ArrayList<String> loadDaysNames(String day) {
        SharedPreferences prefGet = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonNames = prefGet.getString(day + "names", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> names = gson.fromJson(jsonNames, type);

        if (names == null) {
            names = new ArrayList<String>();
        }

        return names;
    }

    public HashMap<String, Boolean> loadDaysSelections(String day) {
        Log.d("load", "loadDaysSelections: ");
        SharedPreferences prefGet = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonChecked = prefGet.getString(day + "checked", null);
        Type type = new TypeToken<HashMap<String, Boolean>>() {}.getType();
        HashMap<String, Boolean> checked = gson.fromJson(jsonChecked, type);

        if (checked == null) {
            checked = new HashMap<String, Boolean>();
        }

        return checked;
    }
}