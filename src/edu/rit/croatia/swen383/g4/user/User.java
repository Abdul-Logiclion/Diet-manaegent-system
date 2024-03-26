package edu.rit.croatia.swen383.g4.user;

import edu.rit.croatia.swen383.g4.food.FoodCollection;

//import edu.rit.croatia.swen383.g4.logs.DailyLog;

/**
 * The type User.
 */
public class User {

  private double currentWeight;
  private double initialWeight;
  private double dailyCalorieIntake;
  private double desiredWeight;
  private double dailyCalorieLimit;

  /**
   * Instantiates a new User.
   */
  public User() {
    // maybe needs some altering
    currentWeight = 68;
    initialWeight = 68;
    dailyCalorieIntake = 2000;
    desiredWeight = 0;
  }

  /**
   * Instantiates a new User.
   *
   * @param currentWeight      the current weight
   * @param initialWeight      the initial weight
   * @param dailyCalorieIntake the daily calorie intake
   * @param desiredWeight      the desired weight
   */
  public User(
    double currentWeight,
    double initialWeight,
    double dailyCalorieIntake,
    double desiredWeight
  ) {
    this.currentWeight = currentWeight;
    this.initialWeight = initialWeight;
    this.dailyCalorieIntake = dailyCalorieIntake;
    this.desiredWeight = desiredWeight;
  }

  /**
   * Add Daily Calorie Intake.
   *
   * @param dailyCalorieIntake the daily calorie intake
   */
  public void updateDailyCalorieIntake(double dailyCalorieIntake) {
    this.dailyCalorieIntake += dailyCalorieIntake;
  }

  /**
   * Gets daily calorie limit.
   *
   * @return the daily calorie limit
   */
  public double getDailyCalorieIntake() {
    return this.dailyCalorieIntake;
  }

  /**
   * Sets daily calorie limit.
   *
   * @param calorieLimit the calorie limit
   */
  public void setDailyCalorieLimit(double calorieLimit) {
    dailyCalorieLimit = calorieLimit;
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