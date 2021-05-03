package com.example.mobts_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class PressureHistoryDetails extends AppCompatActivity {

    Intent intent;

    String date;
    int details;
    int details2;
    int details3;
    String info;

    int index;

    TextView dateText;
    TextView detailsText;
    TextView infoText;
    Button delete;
    Button cancel;
    Button confirm;

    SharedPreferences prefGet;
    SharedPreferences.Editor prefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_history_details);

        intent = getIntent();

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEdit = prefGet.edit();

        date = intent.getStringExtra(PressureHistory.DATE);
        details = intent.getIntExtra(PressureHistory.HPRESS, 0);
        details2 = intent.getIntExtra(PressureHistory.LPRESS, 0);
        details3 = intent.getIntExtra(PressureHistory.PULSE, 0);
        info = intent.getStringExtra(PressureHistory.INFO);
        index = intent.getIntExtra(PressureHistory.INDEX, 0);


        dateText = findViewById(R.id.PressureDate);
        detailsText = findViewById(R.id.PressureDetails);
        infoText = findViewById(R.id.PressureInfo);
        delete = findViewById(R.id.DeletePressure);
        cancel = findViewById(R.id.CancelPressure);
        confirm = findViewById(R.id.ConfirmPressure);

        cancel.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.INVISIBLE);

        dateText.setText("Pvm:" + date);
        detailsText.setText("Verenpaine: " + String.valueOf(details) + "/" + String.valueOf(details2) + " pulssi: " + String.valueOf(details3));
        infoText.setText(info);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm.getVisibility() == View.INVISIBLE){
                    ShowButtons(confirm);
                    ShowButtons(cancel);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancel.getVisibility() != View.INVISIBLE){
                    HideButtons(confirm);
                    HideButtons(cancel);
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diaries.getInstance().getBloodPressures().remove(index);
                Gson gson = new Gson();
                String json = gson.toJson(Diaries.getInstance().getBloodPressures());
                Log.d("JSOn", json);
                prefEdit.putString("painelista", json);
                prefEdit.commit();

                if (cancel.getVisibility() != View.INVISIBLE){
                    HideButtons(confirm);
                    HideButtons(cancel);
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