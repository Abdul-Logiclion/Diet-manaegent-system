package edu.rit.croatia.swen383.g4.dmcontroller;

import edu.rit.croatia.swen383.g4.dmmodel.DietManagerModel;
import edu.rit.croatia.swen383.g4.dmview.DietManagerView;
import edu.rit.croatia.swen383.g4.food.BasicFood;
import edu.rit.croatia.swen383.g4.food.Food;
import edu.rit.croatia.swen383.g4.food.Recipe;
import edu.rit.croatia.swen383.g4.logger.Logger;
import edu.rit.croatia.swen383.g4.logs.DailyLog;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Diet manager controller.
 */
public class DietManagerController {

  private static final Logger LOGGER = new Logger();
  private final DietManagerModel model;
  private final DietManagerView view;

  /**
   * Instantiates a new Diet manager controller.
   *
   * @param view the view
   */
  public DietManagerController(DietManagerView view, DietManagerModel model) {
    this.view = view;
    this.model = model;
  }

  /**
   * Add basic food.
   */
  public void addBasicFood() {
    BasicFood food = view.getInputBasicFood();
    model.addBasicFood(food);
    model.saveCsvData();
    updateFoodList();
    view.setFoodSelectionOptions(getFood());
  }

  /**
   * Add recipe.
   */
  public void addRecipe() {
    Recipe recipe = view.getInputRecipe();
    model.addRecipe(recipe);
    model.saveCsvData();
    updateFoodList();
  }

  /**
   * Update food list.
   */
  public void updateFoodList() {
    List<Food> foods = model.getFood();
    StringBuilder stringBuilder = new StringBuilder();
    for (Food food : foods) {
      stringBuilder.append(food.toString()).append("\n");
    }
    view.setFoodListText(stringBuilder.toString());
  }

  /**
   * Update daily log.
   */
  public void updateDailyLog() {
    DailyLog dailyLog = model.getDailyLogForToday();
    view.setDailyLogText(
        "Calories Consumed Today: " + calculateCalories(dailyLog) + "\n" +
            dailyLog.toString());
  }

  /**
   * Gets basic food.
   *
   * @return the basic food
   */
  public List<String> getBasicFood() {
    return model.getFood().stream()
        .filter(food -> food instanceof BasicFood)
        .map(Food::getName)
        .collect(Collectors.toList());
  }

  /**
   * Get food list.
   *
   * @return the list
   */
  public List<String> getFood() {
    return model.getFood().stream()
        .map(Food::getName)
        .collect(Collectors.toList());
  }

  /**
   * Gets basic food by name.
   *
   * @param foodName the food name
   * @return the basic food by name
   */
  public BasicFood getBasicFoodByName(String foodName) {
    return model.getBasicFoodByName(foodName);
  }

  public Food getFoodByName(String foodName) {
    return model.getFoodByName(foodName);
  }

  /**
   * Add log entry.
   */
  public void addLogEntry() {
    String foodName = view.getSelectedFood();
    double servings;
    try {
      servings = view.getServingAmount();
    } catch (NumberFormatException e) {
      view.showAlert("INVALID INPUT", "Serving amount is not valid. Please enter a valid number.");
      LOGGER.log("Error with servings input: " + e.getMessage());
      LOGGER.log(e.getStackTrace().toString());
      return;
    }

    LocalDate date = view.getSelectedDate();
    if (date == null) {
      view.showAlert("INVALID INPUT", "Selected date invalid. Please select a valid date.");
      return;
    }

    Food food = model.getFoodByName(foodName);
    if (food == null) {
      view.showAlert("INVALID INPUT", "Selected food is not a part of the list. Please select a valid food.");
      return;
    }

    DailyLog dailyLog = model.getDailyLogByDate(date);
    if (dailyLog == null) {
      dailyLog = new DailyLog(date);
      // dailyLog = new DailyLog(date, model.getUser().getCurrentWeight());
      model.getLogCollection().addDailyLog(dailyLog);
    }

    dailyLog.addFood(food, servings);
    model.saveCsvData();
    updateDailyLog();
  } // this method is called when the button is pressed

  /**
   * Display log for selected date.
   */
  public void displayLogForSelectedDate() {
    LocalDate date = view.getSelectedDate();
    DailyLog dailyLog = model.getDailyLogByDate(date);
    if (dailyLog == null)
      view.setDailyLogText("No log for selected date.");
    else {

      view.setDailyLogText(
          "Calories Consumed Today: " + calculateCalories(dailyLog) + "\n" +
              dailyLog.toString());
    }
  }

  /**
   * Calculate calories.
   *
   * @param dailyLog the daily log
   * @return the double
   */
  public double calculateCalories(DailyLog dailyLog) {
    int calories = 0;
    for (Map.Entry<Food, ArrayList<Integer>> entry : dailyLog.getIntakeAmount().entrySet())
      calories += entry.getKey().getCalories() * entry.getValue().get(0);

    return calories;
  }

}