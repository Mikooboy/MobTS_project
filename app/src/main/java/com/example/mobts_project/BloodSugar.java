package com.example.mobts_project;

public class BloodSugar {

    private int bloodSugar;
    private String info;

    public BloodSugar(int bloodSugar){

        this.bloodSugar = bloodSugar;
    }

    public BloodSugar(int bloodSugar, String info){

        this.bloodSugar = bloodSugar;
        this.info = info;
    }
    public int getBloodSugar(){
        return bloodSugar;
    }
    public String getInfo(){
        return info;
    }

    @Override
    public String toString(){
        return "Blood sugar: "+ bloodSugar + " mmo/l " + info;
    }
}
