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

/**
 * Class used for displaying a listView of days and bloodsugar values in the history screen
 * the information of the day is accessed from here
 * @author Julius Saukonoja
 */

public class SugarsHistoryActivity extends AppCompatActivity {

    public static final String DATE = "com.example.MobTS_project.MESSAGE_sugar1";
    public static final String SUGARS = "com.example.exercise_6.MESSAGE_sugar2";
    public static final String INFO = "com.example.exercise_6.MESSAGE_sugar3";
    public static final String INDEX = "com.example.exercise_6.MESSAGE_sugar4";

    ListView sugarsList;

    SharedPreferences prefGet;
    SharedPreferences.Editor prefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugars_history);

        sugarsList = findViewById(R.id.SugarsList);

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEdit = prefGet.edit();

        sugarsList.setAdapter(new ArrayAdapter<BloodSugar>(
                this,
                android.R.layout.simple_list_item_1,
                Diaries.getInstance().getBloodSugars()
        ));
        sugarsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("TAG", "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(SugarsHistoryActivity.this, SugarsHistoryDetails.class);
                nextActivity.putExtra(DATE, Diaries.getInstance().getBloodSugars().get(i).getDate());
                nextActivity.putExtra(SUGARS, Diaries.getInstance().getBloodSugars().get(i).getBloodSugar());
                nextActivity.putExtra(INFO, Diaries.getInstance().getBloodSugars().get(i).getInfo());
                nextActivity.putExtra(INDEX, i);
                startActivity(nextActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gson gson = new Gson();
        String json = prefGet.getString("sokerilista", null);
        Type type = new TypeToken<ArrayList<BloodSugar>>() {}.getType();
        Diaries.getInstance().setBloodSugars(gson.fromJson(json, type));

        if (Diaries.getInstance().getBloodSugars() == null) {
            Diaries.getInstance().setBloodSugars(new ArrayList<BloodSugar>());
        }
    }
}