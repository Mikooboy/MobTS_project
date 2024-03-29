package com.example.mobts_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

/**
 * Shows info on saved steps and dates, saved to StepsList at StepData
 * @author Roope Jantunen
 */
public class ActivityHistoryDetails extends AppCompatActivity {

    Intent intent;
    String date;
    int steps;
    Button deleteButton;
    Button confirmButton;
    Button cancelButton;
    TextView dateText;
    TextView stepsText;
    int index;
    SharedPreferences prefGet;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        intent = getIntent();
        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEditor = prefGet.edit();

        date = intent.getStringExtra(ActivityHistory.DATE);
        steps = intent.getIntExtra(ActivityHistory.STEPS, 0);
        index = intent.getIntExtra(ActivityHistory.INDEX, 0);
        Log.d("Index", ""+index);
        Log.d("Date", ""+ date);
        Log.d("Steps", ""+ steps);


        dateText = findViewById(R.id.dateTextView);
        stepsText = findViewById(R.id.stepsTextView);
        cancelButton = findViewById(R.id.cancelDeleteButton);
        confirmButton = findViewById(R.id.confirmDeleteButton);
        deleteButton = findViewById(R.id.deleteButton);

        confirmButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);

        dateText.setText(date);
        stepsText.setText(String.valueOf(steps));


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // makes cancel and confirm buttons visible to verify deleting
                if (confirmButton.getVisibility() == View.INVISIBLE){
                    ShowButton(confirmButton);
                    ShowButton(cancelButton);
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hides buttons so deleting is canceled
                if (cancelButton.getVisibility() != View.INVISIBLE){
                    HideButton(confirmButton);
                    HideButton(cancelButton);
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // removes the current element shown from StepList and saves it to sharedPreferences
                StepData.getInstance().getStepsList().remove(index);
                Gson gson = new Gson();
                String json = gson.toJson(StepData.getInstance().getStepsList());
                Log.d("JSOn", json);
                prefEditor.putString("StepList", json);
                prefEditor.commit();
                // puts cancel and confirm buttons back to invisible
                if (cancelButton.getVisibility() != View.INVISIBLE){
                    HideButton(confirmButton);
                    HideButton(cancelButton);
                }
                // returns to ActivityHistory (parent activity)
                Intent parentActivity  = new Intent(ActivityHistoryDetails.this, ActivityHistory.class);
                startActivity(parentActivity);
            }
        });

    }


    //makes button visible
    private void ShowButton(Button button){
        button.setVisibility(View.VISIBLE);
    }

    // makes button invisible
    private void HideButton(Button button){
        button.setVisibility(View.INVISIBLE);
    }

}