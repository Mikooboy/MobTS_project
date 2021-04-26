package com.example.mobts_project;

import java.util.ArrayList;
import java.util.List;

public class Medicines {
    private ArrayList<Medicine> medications;

    private static final Medicines ourInstance = new Medicines();

    public static Medicines getInstance(){
        return ourInstance;
    }

    private Medicines() {
        this.medications = new ArrayList<>();
    }

    public ArrayList<Medicine> getMedications() {
        return this.medications;
    }
}
