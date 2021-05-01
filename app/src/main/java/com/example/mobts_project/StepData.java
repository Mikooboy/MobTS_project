package com.example.mobts_project;

import java.util.ArrayList;

public class StepData {
    private static final StepData ourInstance = new StepData();
    private ArrayList<Steps> stepsList;

    public static StepData getInstance() {
        return ourInstance;
    }

    private StepData(){
        stepsList = new ArrayList<>();
    }
    public void setStepsList(ArrayList<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    public ArrayList<Steps> getStepsList(){
        return stepsList;
    }


}
