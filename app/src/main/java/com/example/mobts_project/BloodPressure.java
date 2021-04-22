package com.example.mobts_project;

public class BloodPressure {

    private int highPress;
    private int lowPress;
    private int pulse;

    public BloodPressure(int highPress, int lowPress, int pulse){
        this.highPress = highPress;
        this.lowPress = lowPress;
        this.pulse = pulse;

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

    @Override
    public String toString(){
        return "Blood pressure: "+ highPress + "/" + lowPress + " pulse: " + pulse;
    }


}
