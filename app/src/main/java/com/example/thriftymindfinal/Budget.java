package com.example.thriftymindfinal;

public class Budget {
    private String username;
    private double budget;

    public Budget() {
        // Default constructor required for Firebase
    }

    public Budget(String username, double budget) {
        this.username = username;
        this.budget = budget;
    }

    public String getUsername() {
        return username;
    }

    public double getBudget() {
        return budget;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}

