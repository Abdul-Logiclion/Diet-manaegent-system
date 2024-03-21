package edu.rit.croatia.swen383.g4.food;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The type Recipe.
 */
public class Recipe implements Food {

  private String recipeName;
  private Map<BasicFood, Double> basicFoods;

  /**
   * Instantiates a new Recipe.
   *
   * @param recipeName the recipe name
   * @param basicFoods the basic foods
   */
  public Recipe(String recipeName, Map<BasicFood, Double> basicFoods) {
    this.recipeName = recipeName;
    this.basicFoods = basicFoods;
  }

  private List<Food> ingredients;
  private List<Double> ingredientAmounts;

  /**
   * Gets basic foods.
   *
   * @return the basic foods
   */
  public Map<BasicFood, Double> getBasicFoods() {
    return basicFoods;
  }

  @Override
  public String getName() {
    return recipeName;
  }

  @Override
  public String toString() {
    StringBuilder basicFoodsPrint = new StringBuilder();
    int i = 0;
    for (Map.Entry<BasicFood, Double> entry : basicFoods.entrySet()) {
      basicFoodsPrint.append(
        String.format(
          "\t%s - Servings: %.2f\t\t",
          entry.getKey().getName(),
          entry.getValue()
        )
      );
      if (i++ < basicFoods.size() - 1) basicFoodsPrint.append("AND ");
    }
    return (
      "Recipe: \t\n" +
      "\t Recipe Name = " +
      recipeName +
      "\n\t\tBasic Foods for recipe =  " +
      basicFoodsPrint.toString()
    );
  }
}
