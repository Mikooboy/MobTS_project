package com.example.mobts_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySettings extends AppCompatActivity {

    private TextView textView;
    private Button resetButton;
    private Button cancelButton;
    private Button confirmButton;
    private Button setButton;
    private EditText stepGoalText;
    private int stepGoal;
    SharedPreferences.Editor prefEditor;
    SharedPreferences prefGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEditor = prefGet.edit();

        textView = findViewById(R.id.text);
        resetButton = findViewById(R.id.resetButton);
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setVisibility(View.INVISIBLE);
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setVisibility(View.INVISIBLE);
        setButton = findViewById(R.id.setButton);
        stepGoalText = findViewById(R.id.editTextNumber);


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepGoal = Integer.parseInt(stepGoalText.getText().toString());
                Log.d("StepGoal" , String.valueOf(stepGoal));
                prefEditor.putInt("StepGoal", stepGoal);
                prefEditor.commit();

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
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
                ActivityActivity.getInstance().DailyReset();
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