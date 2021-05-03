package com.example.mobts_project.medicine;

import java.util.ArrayList;

/**
 * Simple class to hold the medicine name and reminder days
 * @author Miko Laasanen
 */
public class Medicine {
    private final String name;
    private final ArrayList<String> days;

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
