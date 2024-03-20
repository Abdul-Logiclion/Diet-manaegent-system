package edu.rit.croatia.swen383.g4.user;

import edu.rit.croatia.swen383.g4.food.FoodCollection;
//import edu.rit.croatia.swen383.g3.logs.DailyLog;
public class User {
    private double currentWeight;
    private double initialWeight;
    private double dailyCalorieIntake;
    private double desiredWeight;

    /**
     * Instantiates a new User.
     */
    public User() {
        currentWeight = 0;
        initialWeight = 0;
        dailyCalorieIntake = 0;
        desiredWeight = 0;
    }


    public User(double currentWeight, double initialWeight, double dailyCalorieIntake, double desiredWeight) {
        this.currentWeight = currentWeight;
        this.initialWeight = initialWeight;
        this.dailyCalorieIntake = dailyCalorieIntake;
        this.desiredWeight = desiredWeight;
    }

    /**
     * Sets daily calorie limit.
     *
     * @param calorieLimit the calorie limit
     */
    public void setDailyCalorieLimit(double calorieLimit) {
        dailyCalorieIntake = calorieLimit;
    }

    /**
     * Sets desired weight.
     *
     * @param weight the weight
     */
    public void setDesiredWeight(double weight) {
        desiredWeight = weight;
    }

    /**
     * Gets current weight.
     *
     * @return the current weight
     */
    public double getCurrentWeight() {
        return currentWeight;
    }

    /**
     * Sets current weight.
     *
     * @param weight the weight
     */
    public void setCurrentWeight(double weight) {
        currentWeight = weight;
    }
}