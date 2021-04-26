package com.example.mobts_project;

import java.util.ArrayList;


public class Diaries {

    private ArrayList<BloodPressure> bloodPressures;
    private ArrayList<Calories> calories;
    private ArrayList<BloodSugar> bloodSugars;

    private static final Diaries ourInstance = new Diaries();

    public static Diaries getInstance(){
        return ourInstance;
    }

    private Diaries(){
        bloodPressures = new ArrayList<>();
        calories = new ArrayList<>();
        bloodSugars = new ArrayList<>();
    }
    public ArrayList<BloodPressure> getBloodPressures() {
        return bloodPressures;
    }
    public ArrayList<Calories> getCalories() {
        return calories;
    }
    public ArrayList<BloodSugar> getBloodSugars() {
        return bloodSugars;
    }



}
