package com.example.mobts_project;

public class Calories {

    int totalCal;

    public Calories(){
        totalCal = 0;
    }

    public void addCalories(int added){
        totalCal += added;
    }
    public int getTotalCalories(){
        return totalCal;
    }
}
