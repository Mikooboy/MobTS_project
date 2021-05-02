package com.example.mobts_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityHistoryDetails extends AppCompatActivity {

    Intent intent;
    String date;
    String steps;
    Button deleteButton;
    Button confirmButton;
    Button cancelButton;
    TextView dateText;
    TextView stepsText;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        intent = getIntent();

        date = intent.getStringExtra(ActivityHistory.DATE);
        steps = intent.getStringExtra(ActivityHistory.STEPS);
        index = Integer.parseInt(intent.getStringExtra(ActivityHistory.INDEX));
        Log.d("Index", ""+index);

        dateText = findViewById(R.id.dateTextView);
        stepsText = findViewById(R.id.stepsTextView);
        cancelButton = findViewById(R.id.cancelDeleteButton);
        confirmButton = findViewById(R.id.confirmDeleteButton);
        deleteButton = findViewById(R.id.deleteButton);

        confirmButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);

        dateText.setText(date);
        stepsText.setText(steps);


        Log.d("Date", date);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmButton.getVisibility() == View.INVISIBLE){
                    ShowButtons(confirmButton);
                    ShowButtons(cancelButton);
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelButton.getVisibility() != View.INVISIBLE){
                    HideButtons(confirmButton);
                    HideButtons(cancelButton);
                }
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepData.getInstance().getStepsList().remove(index);
                if (cancelButton.getVisibility() != View.INVISIBLE){
                    HideButtons(confirmButton);
                    HideButtons(cancelButton);
                }

            }
        });
    }
    private void ShowButtons(Button button){

        button.setVisibility(View.VISIBLE);

    }
    private void HideButtons(Button button){
        button.setVisibility(View.INVISIBLE);

    }

}