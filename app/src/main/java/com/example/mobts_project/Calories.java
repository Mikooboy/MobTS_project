package com.example.mobts_project;

public class Calories {

    int totalCal;

    public Calories(int totalCal){

        this.totalCal = totalCal;
    }


    public void addCalories(int added){
        totalCal += added;
    }
    public void removeCalories(int remove){
        totalCal -= remove;
    }
    public int getTotalCalories(){
        return totalCal;
    }
    public void resetCalories(){
        totalCal = 0;
    }

    @Override
    public String toString(){
        return "Kalorit: " + totalCal + " Kcal";
    }
}
