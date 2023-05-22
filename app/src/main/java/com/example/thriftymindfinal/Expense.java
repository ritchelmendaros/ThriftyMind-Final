package com.example.thriftymindfinal;

public class Expense {
    private String name;
    private double budget;

    public Expense() {
        // Default constructor required for Firebase
    }

    public Expense(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }
}

