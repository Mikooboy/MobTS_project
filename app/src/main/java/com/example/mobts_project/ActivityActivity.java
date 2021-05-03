package com.example.mobts_project;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityActivity extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    SensorManager sensorManager;
    Sensor StepSensor;

    int stepCounter = 0;
    int previousSteps = 0;
    int currentSteps = 0;
    int stepGoal;

    ProgressBar progressBar;
    Button settingButton;
    Button historyButton;

    String dateString;
    String lastDate = "";


    SharedPreferences.Editor prefEditor;
    SharedPreferences prefGet;

    private static ActivityActivity instance;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        instance = this;

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            // ask for permission to access activity information (steps etc.)
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION},1 );
        }

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEditor = prefGet.edit();

        textView = findViewById(R.id.textView);
        textView.setText("0");

        settingButton = findViewById(R.id.setting_button);
        historyButton = findViewById(R.id.history_button);

        stepGoal = prefGet.getInt("StepGoal", 10000);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax(stepGoal);
        progressBar.setProgress(currentSteps);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // check if device has step counter sensor
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!= null){
            StepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        } else{
            textView.setText("Sensoria ei havaittu");

        }
        settingButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(ActivityActivity.this, ActivitySettings.class);
                startActivity(nextActivity);
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadList();
                Intent nextActivity = new Intent(ActivityActivity.this, ActivityHistory.class);
                startActivity(nextActivity);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        LoadDate();
        dateString = LocalDate.now().format(DateTimeFormatter.ofPattern(" dd.MM.yyyy", new Locale("fi")));
        if(lastDate.equals("")){
            String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern(" dd.MM.yyyy", new Locale("fi")));
            prefEditor.putString("StepDate", startDate);
            prefEditor.apply();
            LoadDate();
            Log.d("date3" , lastDate);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == StepSensor){
            // Gets the number of steps from step counter sensor, since last reboot
            stepCounter = (int) event.values[0];
            // to get daily steps, previous steps are stepCounter values saved on last DailyReset()
            currentSteps = stepCounter - previousSteps;
            textView.setText(String.valueOf(currentSteps));
            progressBar.setProgress(currentSteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        // updates  step goal and progress bar in case user has changed it
        stepGoal = prefGet.getInt("StepGoal", 10000);
        progressBar.setMax(stepGoal);
        // updates previousSteps and lastDate
        LoadSteps();
        LoadDate();

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.registerListener(this, StepSensor, SensorManager.SENSOR_DELAY_UI);
        }
        // Updates progress bar and text
        textView.setText(String.valueOf(currentSteps));
        progressBar.setProgress(currentSteps);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPause() {
        super.onPause();
        prefEditor.putInt("savedSteps" , currentSteps);
    }
    /**
     * Makes previousSteps equal to stepCounters value saved on DailyReset()
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void LoadSteps(){

        previousSteps = prefGet.getInt("steps",0);
    }

    /**
     * Makes lastDate equal to date saved on DailyReset()
     */
    private void LoadDate(){

        lastDate = prefGet.getString("StepDate", "");
    }

    /**
     * Gets list from StepData singleton class, makes it into a json string and saves it to sharedPreferences.
     * Saves stepCounters value and current date to sharedPreferences
     * updates text and progress bar
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void DailyReset(){
        LoadDate();
        LoadSteps();

        StepData.getInstance().getStepsList().add(new Steps(currentSteps, lastDate));
        Gson gson = new Gson();
        String json = gson.toJson(StepData.getInstance().getStepsList());
        Log.d("JSOn", json);
        prefEditor.putString("StepList", json);
        prefEditor.putInt("steps", stepCounter);
        prefEditor.putString("StepDate", dateString);
        prefEditor.commit();
        textView.setText(String.valueOf(currentSteps));
        progressBar.setProgress(currentSteps);
    }

    /**
     * Gets json string from sharedPreferences, makes it into a ArrayList and sets it into StepData singleton class
     * if StepData has no list it makes a empty one
     */
    public void LoadList() {
        Gson gson = new Gson();
        String json = prefGet.getString("StepList", null);
        Type type = new TypeToken<ArrayList<Steps>>() {}.getType();
        StepData.getInstance().setStepsList(gson.fromJson(json, type));

        if (StepData.getInstance().getStepsList() == null) {
            StepData.getInstance().setStepsList(new ArrayList<Steps>());
        }
    }
    public static ActivityActivity getInstance(){
        return instance;
    }

}