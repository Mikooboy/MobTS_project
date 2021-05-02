package com.example.mobts_project;

import java.util.ArrayList;


public class Diaries {

    private ArrayList<BloodPressure> bloodPressures;
    private ArrayList<BloodSugar> bloodSugars;

    private static final Diaries ourInstance = new Diaries();

    public static Diaries getInstance(){
        return ourInstance;
    }

    private Diaries(){
        bloodPressures = new ArrayList<>();
        bloodSugars = new ArrayList<>();
    }
    public ArrayList<BloodPressure> getBloodPressures() {
        return bloodPressures;
    }
    public ArrayList<BloodSugar> getBloodSugars() {
        return bloodSugars;
    }
    public ArrayList<BloodPressure> setBloodPressures(ArrayList<BloodPressure> list) {
        return bloodPressures = list;
    }
    public ArrayList<BloodSugar> setBloodSugars(ArrayList<BloodSugar> list) {
        return bloodSugars = list;
    }




}
