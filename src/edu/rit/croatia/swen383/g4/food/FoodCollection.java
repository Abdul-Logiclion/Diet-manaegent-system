package edu.rit.croatia.swen383.g4.food;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Food collection.
 */
public class FoodCollection {

  private List<Food> foods;

  /**
   * Instantiates a new Food collection.
   */
  public FoodCollection() {
    foods = new ArrayList<>();
  }

  /**
   * Add food.
   *
   * @param food the food
   */
  public void addFood(Food food) {
    foods.add(food);
  }

  /**
   * Gets foods.
   *
   * @return the foods
   */
  public List<Food> getFoods() {
    return foods;
  }

  /**
   * Gets food by name.
   *
   * @param foodName the food name
   * @return the food by name
   */
  public Food getFoodByName(String foodName) {
    for (Food food : foods) {
      if (food.getName().equalsIgnoreCase(foodName)) {
        return food;
      }
    }
    return null;
  }
}
