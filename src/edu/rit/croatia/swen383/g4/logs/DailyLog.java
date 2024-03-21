package edu.rit.croatia.swen383.g4.logs;

import edu.rit.croatia.swen383.g4.food.Food;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Daily log.
 */
public class DailyLog {

  private LocalDate date;
  // intakeAmount is a map of food which contains duplicate values for integers
  // which represent the amount of servings of that food
  private Map<Food, ArrayList<Integer>> intakeAmount;
  private double weight;

  /**
   * Instantiates a new Daily log.
   *
   * @param date   the date
   * @param weight the weight
   */
  public DailyLog(LocalDate date, double weight) {
    this.date = date;
    this.weight = weight;
    this.intakeAmount = new HashMap<>();
  }

  /**
   * Instantiates a new Daily log.
   *
   * @param date the date
   */
  public DailyLog(LocalDate date) {
    this.date = date;
    this.intakeAmount = new HashMap<>();
  }

  /**
   * Gets date.
   *
   * @return the date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Sets date.
   *
   * @param date the date
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

  /**
   * Gets intakeAmount.
   *
   * @return the intakeAmount
   */
  public Map<Food, ArrayList<Integer>> getIntakeAmount() {
    return intakeAmount;
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
    intakeAmount.put(food, counts);
  }

  @Override
  public String toString() {
    return ("DailyLog: \t\n" +
        "\t Date = " +
        date +
        "\t Intake = " +
        intakeAmount +
        "\t Weight = " +
        weight);
  }
}