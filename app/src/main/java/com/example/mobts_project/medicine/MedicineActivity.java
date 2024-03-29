package com.example.mobts_project.medicine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobts_project.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Main activity in the medicine tab
 * @author Miko Laasanen
 */
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

        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(this.date);

        updateReminders();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = MedicineLists.getInstance().getTodaysMedications(this);

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        DaysDataHandler.getInstance().saveDay(names, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = MedicineLists.getInstance().getTodaysMedications(this);

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        DaysDataHandler.getInstance().saveDay(names, this);
    }

    /**
     * Opens the dialog to add a medicine
     * @param view
     */
    public void openAddMedicine(View view) {
        AddMedicine addMedicine = new AddMedicine();
        addMedicine.show(getSupportFragmentManager(), "aMedicine");

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = MedicineLists.getInstance().getTodaysMedications(this);

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        DaysDataHandler.getInstance().saveDay(names, this);
    }

    /**
     * Opens the dialog to remove a medicine
     * @param view
     */
    public void openRemoveMedicine(View view) {
        RemoveMedicine removeMedicine = new RemoveMedicine();
        removeMedicine.show(getSupportFragmentManager(), "rMedicine");

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = MedicineLists.getInstance().getTodaysMedications(this);

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        DaysDataHandler.getInstance().saveDay(names, this);
    }

    /**
     * Updates the reminder listView on the activity after a change
     * Also sets the checkbox states
     */
    public void updateReminders() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = MedicineLists.getInstance().getTodaysMedications(this);

        Log.d("medicines", "updateReminders: " + medicines);

        ListView lv = findViewById(R.id.medicationList);

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }


        if (names.size() > 0) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, names) {
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

        HashMap<String, Boolean> checked = DaysDataHandler.getInstance().loadDaysSelections(this.date, this);
        for (int i = 0; i < checked.size(); i++) {
            try {
                Log.d("test", "updateReminders: " + checked);
                Boolean check = checked.get(names.get(i));
                lv.setItemChecked(i, check);
            } catch (Exception e) {
                //  Block of code to handle errors
            }
        }
    }

    /**
     * Saves all the medicines in a list
     */
    public void saveMedicines() {
        SharedPreferences prefPut = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();

        Gson gson = new Gson();
        String json = gson.toJson(MedicineLists.getInstance().getMedications());

        prefEditor.putString("MedicineList", json);
        prefEditor.apply();
    }

    /**
     * Loads all the medicines from a list
     */
    public void loadMedicines() {
        SharedPreferences prefGet = getSharedPreferences("MobTS_project", Activity.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefGet.getString("MedicineList", null);
        Type type = new TypeToken<ArrayList<Medicine>>() {
        }.getType();
        MedicineLists.getInstance().setMedications(gson.fromJson(json, type));

        if (MedicineLists.getInstance().getMedications() == null) {
            MedicineLists.getInstance().setMedications(new ArrayList<Medicine>());
        }
    }
}