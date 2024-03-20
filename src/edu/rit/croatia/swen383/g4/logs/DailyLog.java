package edu.rit.croatia.swen383.g4.logs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.rit.croatia.swen383.g4.food.Food;


public class DailyLog {
    private LocalDate date;
    private Map<Food, ArrayList<Integer>> intake;
    private double weight;

    
    public DailyLog(LocalDate date, double weight) {
        this.date = date;
        this.weight = weight;
        this.intake = new HashMap<>();
    }

   
    public DailyLog(LocalDate date) {
        this.date = date;
        this.intake = new HashMap<>();
    }

    public LocalDate getDate() {
        return date;
    }

 
    public void setDate(LocalDate date) {
        this.date = date;
    }

   
    public Map<Food, ArrayList<Integer>> getIntake() {
        return intake;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Add food.
     *
     * @param food  the food
     * @param count the count
     */
    public void addFood(Food food, double count) {
        ArrayList<Integer> counts = new ArrayList<>();
        counts.add(Math.round((float) count));
        intake.put(food, counts);
    }

    @Override
    public String toString() {
        return "DailyLog: \t\n" + "\t Date = " + date + "\t Intake = " + intake + "\t Weight = " + weight;
    }
}