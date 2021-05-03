package com.example.mobts_project.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobts_project.R;

/**
 * Class used for displaying a listView of dates in the history screen
 * Days history is accessed from here
 * @author Miko Laasanen
 */
public class MedicineHistory extends AppCompatActivity {
    public static final String EXTRA = "com.example.MobTS_project.EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_history);
        

        ListView lv = findViewById(R.id.datesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DaysDataHandler.getInstance().loadDays(this));
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
}