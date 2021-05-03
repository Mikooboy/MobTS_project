package com.example.mobts_project;

/**
 * Defines what values are needed for blood pressure
 * @author Roope Jantunen
 */
public class BloodPressure {

    private int highPress;
    private int lowPress;
    private int pulse;
    private String info;
    private String date;

    public BloodPressure(int highPress, int lowPress, int pulse){
        this.highPress = highPress;
        this.lowPress = lowPress;
        this.pulse = pulse;
    }
    public BloodPressure(int highPress, int lowPress, int pulse,String info, String date){
        this.highPress = highPress;
        this.lowPress = lowPress;
        this.pulse = pulse;
        this.info = info;
        this.date = date;
    }


    public int getHighPress(){
        return highPress;
    }
    public int getLowPress(){
        return lowPress;
    }
    public int getPulse(){
        return pulse;
    }
    public String getInfo(){
        return info;
    }
    public String getDate(){
        return date;
    }


    @Override
    public String toString(){
        return date;
    }


}
