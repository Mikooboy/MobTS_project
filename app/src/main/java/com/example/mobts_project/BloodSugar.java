package com.example.mobts_project;

/**
 * Defines what values are needed for blood sugar
 * @author Roope Jantunen
 */
public class BloodSugar {

    private int bloodSugar;
    private String info;
    private String date;

    public BloodSugar(int bloodSugar){

        this.bloodSugar = bloodSugar;
    }

    public BloodSugar(int bloodSugar, String info, String date){

        this.bloodSugar = bloodSugar;
        this.info = info;
        this.date = date;
    }
    public int getBloodSugar(){
        return bloodSugar;
    }
    public String getInfo(){
        return info;
    }
    public String getDate(){
        return date;
    }

    @Override
    public String toString(){
        return date + ": Blood sugar: "+ bloodSugar + " mmo/l " + info;
    }
}
