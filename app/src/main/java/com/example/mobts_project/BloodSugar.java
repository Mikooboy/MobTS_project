package com.example.mobts_project;

import androidx.annotation.NonNull;

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
    public String toString() {
        if (info == null){
            info = "";
        }
        return bloodSugar + " " + info;
    }
}
