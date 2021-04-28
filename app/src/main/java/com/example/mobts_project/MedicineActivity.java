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

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MedicineActivity extends AppCompatActivity {
    private String date;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Intent intent = getIntent();

        loadMedicines();

        LocalDate localDate = LocalDate.now();

        this.date = localDate.format(DateTimeFormatter.ofPattern("E, dd.MM.yyyy", new Locale("fi")));
        Log.d("test", "day: " + this.day);

        TextView dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(this.date);

        updateReminders();
    }

    public void openAddMedicine(View v) {
        AddMedicine addMedicine = new AddMedicine();
        addMedicine.show(getSupportFragmentManager(), "aMedicine");
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

                    Log.d("test", "1: " + medicines);
                    Log.d("test", "2: " + newMedicineList);

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
        Medicines instance = Medicines.getInstance();

        ListView lv = findViewById(R.id.medicationList);
        MedicationListAdapter adapter = new MedicationListAdapter(this, R.layout.medication_list_element, instance.getTodaysMedications());
        lv.setAdapter(adapter);

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
}