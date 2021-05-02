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
import android.view.WindowManager;
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
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION},1 );
        }

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEditor = prefGet.edit();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textView = findViewById(R.id.textView);
        textView.setText("0");

        settingButton = findViewById(R.id.setting_button);
        historyButton = findViewById(R.id.history_button);

        stepGoal = prefGet.getInt("StepGoal", 10000);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax(stepGoal);
        progressBar.setProgress(currentSteps);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!= null){
            StepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        } else{
            textView.setText("Sensor not found");

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
        Log.d("date" , " testi1: "+lastDate);
        LoadDate();
        Log.d("date" , " testi2: "+lastDate);
        dateString = LocalDate.now().format(DateTimeFormatter.ofPattern(" dd.MM.yyyy", new Locale("fi")));
        if(lastDate.equals("")){
            String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern(" dd.MM.yyyy", new Locale("fi")));
            prefEditor.putString("date", startDate);
            prefEditor.apply();
            LoadDate();
            Log.d("date3" , lastDate);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == StepSensor){
            stepCounter = (int) event.values[0];
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
        stepGoal = prefGet.getInt("StepGoal", 10000);
        progressBar.setMax(stepGoal);
        LoadSteps();
        LoadDate();
        Log.d("Step", String.valueOf(stepGoal));

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.registerListener(this, StepSensor, SensorManager.SENSOR_DELAY_UI);
        }
        textView.setText(String.valueOf(currentSteps));
        progressBar.setProgress(currentSteps);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.registerListener(this, StepSensor, SensorManager.SENSOR_DELAY_UI);
        }
        prefEditor.putInt("savedSteps" , currentSteps);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)

    private void LoadSteps(){

        previousSteps = prefGet.getInt("steps",0);
    }

    private void LoadDate(){

        lastDate = prefGet.getString("date", "");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void DailyReset(){
        LoadDate();
        LoadSteps();
        Log.d("Step1", "" + previousSteps);
        Log.d("Step2", "" + stepCounter);
        Log.d("Step3", "" +currentSteps);


        StepData.getInstance().getStepsList().add(new Steps(currentSteps, lastDate));
        Gson gson = new Gson();
        String json = gson.toJson(StepData.getInstance().getStepsList());
        Log.d("JSOn", json);
        prefEditor.putString("lista", json);
        prefEditor.putInt("steps", stepCounter);
        prefEditor.putString("date", dateString);
        prefEditor.commit();
        textView.setText(String.valueOf(currentSteps));
        progressBar.setProgress(currentSteps);
    }
    public void LoadList() {
        Gson gson = new Gson();
        String json = prefGet.getString("lista", null);
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