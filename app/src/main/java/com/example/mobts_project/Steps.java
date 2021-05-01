package com.example.mobts_project;

import androidx.annotation.NonNull;

public class Steps {
    int steps;
    String date;

    public Steps(int steps, String date){
        this.steps = steps;
        this.date = date;
    }

    public int getSteps(){
        return steps;
    }
    public String getDate(){
        return date;
    }

    @NonNull
    @Override
    public String toString() {
        return date + " " + steps;
    }
}
