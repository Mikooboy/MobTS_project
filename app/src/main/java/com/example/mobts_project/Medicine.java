package com.example.mobts_project;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class Medicine {
    private String name;
    private ArrayList<String> days;


    public Medicine(String name, ArrayList<String> days) {
        this.name = name;
        this.days = days;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getDays() {
        return this.days;
    }
}
