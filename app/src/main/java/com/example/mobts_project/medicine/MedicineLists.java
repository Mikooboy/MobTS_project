package com.example.mobts_project.medicine;

import android.app.Activity;
import android.widget.TextView;

import com.example.mobts_project.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Singleton class for managing the medicine lists
 */
public class MedicineLists {
    private static final MedicineLists ourInstance = new MedicineLists();
    private ArrayList<Medicine> medications;
    private ArrayList<Medicine> todaysMedications;

    private MedicineLists() {
        this.medications = new ArrayList<>();
        this.todaysMedications = new ArrayList<>();
    }

    public static MedicineLists getInstance() {
        return ourInstance;
    }

    /**
     * Function to get all the medicines available
     * @return List of all medicines
     */
    public ArrayList<Medicine> getMedications() {
        return this.medications;
    }

    /**
     * Function to set the medicines
     * Used for example when a medicine is added or removed
     * @param medications
     */
    public void setMedications(ArrayList<Medicine> medications) {
        this.medications = medications;
    }

    /**
     * Function to get all the medicines but only for the day that is open on the activity
     * @param activity
     * @return ArrayList of medicines
     */
    public ArrayList<Medicine> getTodaysMedications(Activity activity) {
        this.todaysMedications = new ArrayList<>();

        TextView dateText = activity.findViewById(R.id.dateText);
        String date = dateText.getText().toString();
        String day = date.substring( 0, date.indexOf(","));

        for (int i = 0; i < this.medications.size(); i++) {
            if (medications.get(i).getDays().contains(day)) {
                this.todaysMedications.add(medications.get(i));
            }
        }
        return this.todaysMedications;
    }
}
