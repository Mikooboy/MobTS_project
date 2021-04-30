package com.example.mobts_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;

public class MedicineActivity extends AppCompatActivity {
    private String date;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Intent intent = getIntent();

        Button history = findViewById(R.id.historyButton);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextActivity = new Intent(MedicineActivity.this, MedicineHistory.class);
                startActivity(nextActivity);

            }
        });

        loadMedicines();

        LocalDate localDate = LocalDate.now();

        this.date = localDate.format(DateTimeFormatter.ofPattern("E, dd.MM.yyyy", new Locale("fi")));

        TextView dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(this.date);

        updateReminders();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = Medicines.getInstance().getTodaysMedications();

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        saveDays(names);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = Medicines.getInstance().getTodaysMedications();

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        saveDays(names);
    }

    public void openAddMedicine(View v) {
        AddMedicine addMedicine = new AddMedicine();
        addMedicine.show(getSupportFragmentManager(), "aMedicine");

        loadDaysSelections(this.date);
    }

    public void openRemoveMedicine(View v) {
        Dialog dialog = new Dialog(MedicineActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.remove_medicine);
        dialog.show();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = Medicines.getInstance().getMedications();

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        Button remove = dialog.findViewById(R.id.button3);
        Button cancel = dialog.findViewById(R.id.button4);

        if (names.size() > 0) {
            ListView lv = dialog.findViewById(R.id.removeMedicineList);
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

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Medicine> newMedicineList = new ArrayList<>();

                    int count = lv.getChildCount();
                    for(int i = 0;i < count;i++){
                        if(!lv.isItemChecked(i)){
                            //do your task/work
                            newMedicineList.add(medicines.get(i));
                        }
                    }

                    Medicines.getInstance().setMedications(newMedicineList);
                    dialog.dismiss();
                    updateReminders();
                }
            });
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                updateReminders();
            }
        });
    }

    public void updateReminders() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = Medicines.getInstance().getTodaysMedications();

        Log.d("medicines", "updateReminders: " + medicines);

        ListView lv = findViewById(R.id.medicationList);

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }


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

        HashMap<String, Boolean> checked = loadDaysSelections(this.date);
        for (int i = 0; i < checked.size(); i++) {
            try {
                Log.d("test", "updateReminders: " + checked);
                Boolean check = checked.get(names.get(i));
                lv.setItemChecked(i, check);
            } catch(Exception e) {
                //  Block of code to handle errors
            }
        }

        saveMedicines();
    }

    public void saveMedicines() {
        SharedPreferences prefPut = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();

        Gson gson = new Gson();
        String json = gson.toJson(Medicines.getInstance().getMedications());

        prefEditor.putString("MedicineList", json);
        prefEditor.apply();
    }

    public void loadMedicines() {
        SharedPreferences prefGet = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefGet.getString("MedicineList", null);
        Type type = new TypeToken<ArrayList<Medicine>>() {}.getType();
        Medicines.getInstance().setMedications(gson.fromJson(json, type));

        if (Medicines.getInstance().getMedications() == null) {
            Medicines.getInstance().setMedications(new ArrayList<Medicine>());
        }
    }

    public void saveDays(ArrayList<String> names) {
        Log.d("save", "saveDays: ");
        ListView lv = findViewById(R.id.medicationList);
        HashMap<String, Boolean> checked = new HashMap<>();

        Log.d("names", "saveDays: " + names);

        int count = lv.getChildCount();
        for(int i = 0; i < count; i++){
            checked.put(names.get(i), lv.isItemChecked(i));
        }

        SharedPreferences prefPut = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();

        Gson gson = new Gson();

        ArrayList<String> dates = loadDays();

        if (!dates.contains(this.date)) {
            dates.add(this.date);
        }

        String jsonDates = gson.toJson(dates);
        String jsonNames = gson.toJson(names);
        String jsonChecked = gson.toJson(checked);

        prefEditor.putString("dates", jsonDates);
        prefEditor.putString(this.date + "names", jsonNames);
        prefEditor.putString(this.date + "checked", jsonChecked);
        prefEditor.apply();
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