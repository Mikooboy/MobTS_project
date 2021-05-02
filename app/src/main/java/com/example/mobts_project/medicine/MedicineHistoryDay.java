package com.example.mobts_project.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobts_project.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MedicineHistoryDay extends AppCompatActivity {
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_history_day);

        Intent intent = getIntent();
        int item = intent.getIntExtra(MedicineHistory.EXTRA, 0);

        TextView dateText = findViewById(R.id.dateText);

        this.date = DaysDataHandler.getInstance().loadDays(this).get(item);
        dateText.setText(this.date);

        ArrayList<String> names = DaysDataHandler.getInstance().loadDaysNames(this.date, this);

        ListView lv = findViewById(R.id.medicationList);

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

    @Override
    protected void onPause() {
        super.onPause();

        ArrayList<String> names = DaysDataHandler.getInstance().loadDaysNames(this.date, this);
        DaysDataHandler.getInstance().saveDays(names, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ArrayList<String> names = DaysDataHandler.getInstance().loadDaysNames(this.date, this);
        DaysDataHandler.getInstance().saveDays(names, this);
    }
}