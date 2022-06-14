package com.example.midterm.data.models;

public class Purpose {
    public int id;
    public String purpose;
    public float cost;
    public String date;

    public Purpose(int id, String purpose, float cost, String date) {
        this.id = id;
        this.purpose = purpose;
        this.cost = cost;
        this.date = date;
    }
    public Purpose(String purpose, float cost, String date) {
        this.purpose = purpose;
        this.cost = cost;
        this.date = date;
    }
}
