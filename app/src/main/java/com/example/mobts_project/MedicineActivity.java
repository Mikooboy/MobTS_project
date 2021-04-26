package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Intent intent = getIntent();

        Medicines instance = Medicines.getInstance();
        instance.getMedications().add(new Medicine("Burana 600mg"));

        ListView lv = findViewById(R.id.medicationList);
        MedicationListAdapter adapter = new MedicationListAdapter(this, R.layout.medication_list_element, instance.getMedications());
        lv.setAdapter(adapter);
    }
}