package com.example.mobts_project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Medicines {
    private ArrayList<Medicine> medications;
    private ArrayList<Medicine> todaysMedications;

    private static final Medicines ourInstance = new Medicines();

    public static Medicines getInstance(){
        return ourInstance;
    }

    private Medicines() {
        this.medications = new ArrayList<>();
        this.todaysMedications = new ArrayList<>();
    }

    public void setMedications(ArrayList<Medicine> medications) {
        this.medications = medications;
    }

    public ArrayList<Medicine> getMedications() {
        return this.medications;
    }

    public ArrayList<Medicine> getTodaysMedications() {
        this.todaysMedications = new ArrayList<>();

        LocalDate localDate = LocalDate.now();
        String day = localDate.format(DateTimeFormatter.ofPattern("E", new Locale("fi")));

        for(int i = 0; i < this.medications.size(); i++) {
            if (medications.get(i).getDays().contains(day)) {
                this.todaysMedications.add(medications.get(i));
            }
        }
        return this.todaysMedications;
    }
}
