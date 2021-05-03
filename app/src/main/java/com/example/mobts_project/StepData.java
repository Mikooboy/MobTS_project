package com.example.mobts_project;

import java.util.ArrayList;

/**
 * Class that holds list where steps are saved
 * @author Roope Jantunen
 */
public class StepData {
    private static final StepData ourInstance = new StepData();
    private ArrayList<Steps> stepsList;

    public static StepData getInstance() {
        return ourInstance;
    }

    private StepData(){
        stepsList = new ArrayList<>();
    }

    /**
     * Allows to set the stepList
     * @param stepsList
     */
    public void setStepsList(ArrayList<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    public ArrayList<Steps> getStepsList(){
        return stepsList;
    }


}
