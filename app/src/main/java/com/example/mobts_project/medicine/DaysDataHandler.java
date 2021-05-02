package com.example.mobts_project.medicine;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobts_project.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class DaysDataHandler {
    private static final DaysDataHandler ourInstance = new DaysDataHandler();

    private DaysDataHandler() {
        LocalDate localDate = LocalDate.now();

    }

    public static DaysDataHandler getInstance() {
        return ourInstance;
    }

    public void saveDays(ArrayList<String> names, Activity activity) {
        TextView dateText = activity.findViewById(R.id.dateText);
        String date = dateText.getText().toString();

        ListView lv = activity.findViewById(R.id.medicationList);
        HashMap<String, Boolean> checked = new HashMap<>();

        int count = names.size();
        Log.d("count", "saveDays: " + count);
        for (int i = 0; i < count; i++) {
            checked.put(names.get(i), lv.isItemChecked(i));
        }

        SharedPreferences prefPut = activity.getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();

        Gson gson = new Gson();

        ArrayList<String> dates = loadDays(activity);

        if (!dates.contains(date)) {
            dates.add(date);
        }

        String jsonDates = gson.toJson(dates);
        String jsonNames = gson.toJson(names);
        String jsonChecked = gson.toJson(checked);

        prefEditor.putString("dates", jsonDates);
        prefEditor.putString(date + "names", jsonNames);
        prefEditor.putString(date + "checked", jsonChecked);
        prefEditor.apply();
    }

    public ArrayList<String> loadDays(Activity activity) {
        SharedPreferences prefGet = activity.getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonDates = prefGet.getString("dates", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> dates = gson.fromJson(jsonDates, type);

        if (dates == null) {
            dates = new ArrayList<String>();
        }

        return dates;
    }

    public ArrayList<String> loadDaysNames(String day, Activity activity) {
        SharedPreferences prefGet = activity.getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonNames = prefGet.getString(day + "names", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> names = gson.fromJson(jsonNames, type);

        if (names == null) {
            names = new ArrayList<String>();
        }

        return names;
    }

    public HashMap<String, Boolean> loadDaysSelections(String day, Activity activity) {
        SharedPreferences prefGet = activity.getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonChecked = prefGet.getString(day + "checked", null);
        Type type = new TypeToken<HashMap<String, Boolean>>() {
        }.getType();
        HashMap<String, Boolean> checked = gson.fromJson(jsonChecked, type);

        if (checked == null) {
            checked = new HashMap<String, Boolean>();
        }

        return checked;
    }
}
