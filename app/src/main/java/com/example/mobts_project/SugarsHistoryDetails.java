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

/**
 * Class used for displaying information of the day
 * the day's information can be deleted from here as well
 */

public class SugarsHistoryDetails extends AppCompatActivity {

    Intent intent;

    String date;
    int values;
    String info;

    int index;

    TextView dateText;
    TextView valuesText;
    TextView infoText;
    Button delete;
    Button cancel;
    Button confirm;

    SharedPreferences prefGet;
    SharedPreferences.Editor prefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugars_history_details);

        intent = getIntent();

        prefGet = getSharedPreferences("myPrefs", MODE_PRIVATE);
        prefEdit = prefGet.edit();

        date = intent.getStringExtra(SugarsHistoryActivity.DATE);
        values = intent.getIntExtra(SugarsHistoryActivity.SUGARS, 0);
        info = intent.getStringExtra(SugarsHistoryActivity.INFO);
        index = intent.getIntExtra(SugarsHistoryActivity.INDEX, 0);


        dateText = findViewById(R.id.SugarsDate);
        valuesText = findViewById(R.id.SugarValues);
        infoText = findViewById(R.id.Information);
        delete = findViewById(R.id.DeleteSugarsButton);
        cancel = findViewById(R.id.CancelButton);
        confirm = findViewById(R.id.ConfirmButton);

        cancel.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.INVISIBLE);

        dateText.setText("Pvm: " + date);
        valuesText.setText("Verensokeri: " + String.valueOf(values));
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
                Diaries.getInstance().getBloodSugars().remove(index);
                Gson gson = new Gson();
                String json = gson.toJson(Diaries.getInstance().getBloodSugars());
                Log.d("JSOn", json);
                prefEdit.putString("sokerilista", json);
                prefEdit.commit();

                if (cancel.getVisibility() != View.INVISIBLE){
                    HideButtons(confirm);
                    HideButtons(cancel);
                }

                Intent nextActivity = new Intent(SugarsHistoryDetails.this , SugarsHistoryActivity.class);
                startActivity(nextActivity);

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