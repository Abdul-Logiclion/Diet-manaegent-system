package edu.rit.croatia.swen383.g4.food;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Recipe.
 */
public class Recipe implements Food {

  private String recipeName;
  private Map<Food, Double> recipe;
  private List<Food> foods;

  /**
   * Instantiates a new Recipe.
   *
   * @param recipeName the recipe name
   * @param recipe     the recipe
   */
  public Recipe(String recipeName, Map<Food, Double> recipe) {
    this.recipeName = recipeName;
    this.recipe = recipe;

    this.foods = new ArrayList<>();
    for (Map.Entry<Food, Double> entry : recipe.entrySet()) {
      this.foods.add(entry.getKey());
    }
  } // end of Recipe constructor

  public Recipe(String recipeName, Map<Food, Double> recipe, List<Food> foods) {
    this.recipeName = recipeName;
    this.recipe = recipe;
    this.foods = foods;
  }

  public void setFoods(List<Food> foods) {
    this.foods = foods;
  }

  /**
   * Gets recipe.
   *
   * @return the recipe
   */
  public Map<Food, Double> getRecipe() {
    return recipe;
  }

  @Override
  public String getName() {
    return recipeName;
  }

  @Override
  public double getCalories() {
    double caloriesTotal = 0;

    for (Food food : foods) {
      caloriesTotal += food.getCalories();
    }

    return caloriesTotal;
  }

  @Override
  public double getFat() {
    double fatTotal = 0;

    for (Food food : foods) {
      fatTotal += food.getFat();
    }
    return fatTotal;
  }

  @Override
  public double getCarbs() {
    double carbsTotal = 0;

    for (Food food : foods) {
      carbsTotal += food.getCarbs();
    }
    return carbsTotal;
  }

  @Override
  public double getProtein() {
    double proteinTotal = 0;

    for (Food food : foods) {
      proteinTotal += food.getProtein();
    }
    return proteinTotal;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Recipe Name: ").append(getName()).append("\n");
    sb.append("\tFoods for recipe = ");

    for (Map.Entry<Food, Double> entry : recipe.entrySet()) {
      sb
        .append("\t")
        .append(entry.getKey().getName())
        .append(" - Servings: ")
        .append(String.format("%.2f", entry.getValue()))
        .append("\tAND ");
    }

    // Remove the last AND
    if (!getRecipe().isEmpty()) {
      sb.setLength(sb.length() - 5);
    }

    return sb.toString();
  }
}
