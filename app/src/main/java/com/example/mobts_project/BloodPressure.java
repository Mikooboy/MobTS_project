package com.example.mobts_project;

public class BloodPressure {

    private int highPress;
    private int lowPress;
    private int pulse;
    private String info;

    public BloodPressure(int highPress, int lowPress, int pulse){
        this.highPress = highPress;
        this.lowPress = lowPress;
        this.pulse = pulse;
    }
    public BloodPressure(int highPress, int lowPress, int pulse,String info){
        this.highPress = highPress;
        this.lowPress = lowPress;
        this.pulse = pulse;
        this.info = info;
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

    @Override
    public String toString(){
        return "Blood pressure: "+ highPress + "/" + lowPress + " pulse: " + pulse;
    }


}
