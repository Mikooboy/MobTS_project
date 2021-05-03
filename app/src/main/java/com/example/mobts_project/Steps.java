package com.example.mobts_project;

import androidx.annotation.NonNull;

/**
 * Class that holds steps(int) and date(String)
 * @author Roope Jantunen
 */
public class Steps {
    int steps;
    String date;

    public Steps(int steps, String date){
        this.steps = steps;
        this.date = date;
    }

    /**
     * Returns steps
     * @return steps
     */
    public int getSteps(){
        return steps;
    }
    /**
     * Returns date
     * @return date
     */
    public String getDate(){
        return date;
    }

    /**
     * Puts date and steps to String
     * @return date + "Steps: " + steps
     */
    @NonNull
    @Override
    public String toString() {
        return date + "  Steps: " + steps;
    }
}
