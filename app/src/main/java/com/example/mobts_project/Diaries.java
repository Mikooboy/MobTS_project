package com.example.mobts_project;

import java.util.ArrayList;

/**
 * Class that holds lists where blood sugars and blood pressures are saved to
 * @author Roope Jantunen
 */
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

    /**
     * Allows to set the blood pressure list
     * @param list
     */
    public void setBloodPressures(ArrayList<BloodPressure> list) {
        this.bloodPressures = list;
    }
    /**
     * Allows to set the blood sugars list
     * @param list
     */
    public void setBloodSugars(ArrayList<BloodSugar> list) {
        this.bloodSugars = list;
    }




}
