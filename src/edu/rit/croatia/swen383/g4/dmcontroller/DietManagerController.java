package edu.rit.croatia.swen383.g4.dmcontroller;

import edu.rit.croatia.swen383.g4.dmmodel.DietManagerModel;
import edu.rit.croatia.swen383.g4.dmview.DietManagerView;
import edu.rit.croatia.swen383.g4.food.BasicFood;
import edu.rit.croatia.swen383.g4.food.Food;
import edu.rit.croatia.swen383.g4.food.Recipe;
import edu.rit.croatia.swen383.g4.logger.Logger;
import edu.rit.croatia.swen383.g4.logs.DailyLog;
import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;

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
    view.clearFoodInputs();

    view.setFoodSelectionOptions(getFood());
    view.showAlert(
      food.getName() + " Added!",
      "The food was added successfully.",
      Alert.AlertType.INFORMATION
    );
  }

  /**
   * Add recipe.
   */
public void createAndAddRecipe(String recipeName, Map<String, Double> ingredientNamesAndServings) {
    Map<Food, Double> ingredients = new HashMap<>();
    for (Map.Entry<String, Double> entry : ingredientNamesAndServings.entrySet()) {
        Food food = model.getFoodByName(entry.getKey());
        if (food != null) {
            ingredients.put(food, entry.getValue());
        }
    }
    Recipe recipe = new Recipe(recipeName, ingredients);
    model.addRecipe(recipe);
    model.saveCsvData();
    updateFoodList();
    view.setFoodSelectionOptions(getFood());
    view.showAlert("Recipe Added", "The recipe was successfully added.", Alert.AlertType.INFORMATION);
}



  /**
   * Update food list.
   */
  public void updateFoodList() {
    List<Food> foods = model.getFoods();
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
    // getDailyLogByDate BASED on the SELECTED DATE
    DailyLog dailyLog = model.getDailyLogByDate(view.getSelectedDate());
    view.setDailyLogText(
      "Calories Consumed Today: " +
      calculateCalories(dailyLog) +
      "\n" +
      dailyLog.toString()
    );
  }

  /**
   * Get food list.
   *
   * @return the list
   */
  public List<String> getFood() {
    return model
      .getFoods()
      .stream()
      .map(Food::getName)
      .collect(Collectors.toList());
  }

  public List<String> getExercise() {
    return model
            .getFoods()
            .stream()
            .map(Food::getName)
            .collect(Collectors.toList());
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
      view.showAlert(
        "INVALID INPUT",
        "Serving amount is not valid. Please enter a valid number.",
        Alert.AlertType.ERROR
      );

      LOGGER.log("Error with servings input: " + e.getMessage());
      LOGGER.log(e.getStackTrace().toString());
      return;
    }

    LocalDate date = view.getSelectedDate();
    if (date == null) {
      view.showAlert(
        "INVALID INPUT",
        "Selected date invalid. Please select a valid date.",
        Alert.AlertType.ERROR
      );
      return;
    }

    Food food = model.getFoodByName(foodName);
    if (food == null) {
      view.showAlert(
        "INVALID INPUT",
        "Selected food is not a part of the list. Please select a valid food.",
        Alert.AlertType.ERROR
      );

      return;
    }

    DailyLog dailyLog = model.getDailyLogByDate(date);
    if (dailyLog == null) {
      dailyLog = new DailyLog(date);
      model.getLogCollection().addDailyLog(dailyLog);
    }

    dailyLog.addFood(food, servings);
    model.saveCsvData();
    updateDailyLog();
   // view.updatePieChart();
  } // this method is called when the button is pressed

  /**
   * Display log for selected date.
   */
  public void displayLogForSelectedDate() {
    LocalDate date = view.getSelectedDate();
    DailyLog dailyLog = model.getDailyLogByDate(date);
    if (dailyLog == null) view.setDailyLogText(
      "No log for selected date."
    ); else {
      view.setDailyLogText(
        "Calories Consumed To: " + "\n" +   "Calories Burned Today: "+"\n"+
        calculateCalories(dailyLog) +
        "\n" +

        dailyLog.toString()

      );
    }
   // view.updatePieChart();
  }

  /**
   * Calculate calories.
   *
   * @param dailyLog the daily log
   * @return the double
   */
  public double calculateCalories(DailyLog dailyLog) {
    int calories = 0;
    if (dailyLog == null) return 0;
    for (Map.Entry<Food, Double> entry : dailyLog
      .getIntakeAndServing()
      .entrySet()) calories += entry.getKey().getCalories() * entry.getValue();

    return calories;
  }

  /*
   * Gathering nutrients
   */
  public Map<String, Double> getNutrients() {
    Map<String, Double> nutrients = new HashMap<>();

    DailyLog dailyLog = model.getDailyLogByDate(view.getSelectedDate());
    nutrients.put("Calories", 0.0);
    nutrients.put("Fat", 0.0);
    nutrients.put("Carbohydrates", 0.0);
    nutrients.put("Protein", 0.0);

    if (dailyLog != null) {
      double totalCalories = 0;
      double totalFat = 0;
      double totalCarbs = 0;
      double totalProtein = 0;
      for (Map.Entry<Food, Double> entry : dailyLog
        .getIntakeAndServing()
        .entrySet()) {
        totalCalories += entry.getKey().getCalories() * entry.getValue();
        totalFat += entry.getKey().getFat() * entry.getValue();
        totalCarbs += entry.getKey().getCarbs() * entry.getValue();
        totalProtein += entry.getKey().getProtein() * entry.getValue();
      }
      nutrients.put("Calories", totalCalories);
      nutrients.put("Fat", totalFat);
      nutrients.put("Carbohydrates", totalCarbs);
      nutrients.put("Protein", totalProtein);
    }
    return nutrients;
  }
}