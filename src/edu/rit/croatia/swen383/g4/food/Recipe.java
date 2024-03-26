package edu.rit.croatia.swen383.g4.food;

import java.util.Map;

/**
 * The type Recipe.
 */
public class Recipe implements Food {

    private String recipeName;
    private Map<Food, Double> recipe;

    /**
     * Instantiates a new Recipe.
     *
     * @param recipeName the recipe name
     * @param recipe     the recipe
     */
    public Recipe(String recipeName, Map<Food, Double> recipe) {
        this.recipeName = recipeName;
        this.recipe = recipe;
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
        for (Map.Entry<Food, Double> entry : recipe.entrySet()) {
            caloriesTotal += entry.getKey().getCalories() * entry.getValue();
        }

        recipe
                .entrySet()
                .forEach(entry -> {
                    System.out.println(
                            "Recipe1- " + entry.getKey() + " " + entry.getKey().getCalories());
                });

        for (Food food : recipe.keySet()) {
            System.out.println(
                    "Recip2: Name - " + food.getName() + " Protein - " + food.getCalories());
        }

        return caloriesTotal;
    }

    @Override
    public double getFat() {
        double fatTotal = 0;
        for (Map.Entry<Food, Double> entry : recipe.entrySet()) {
            fatTotal += entry.getKey().getFat() * entry.getValue();
        }
        return fatTotal;
    }

    @Override
    public double getCarbs() {
        double carbsTotal = 0;
        for (Map.Entry<Food, Double> entry : recipe.entrySet()) {
            carbsTotal += entry.getKey().getCarbs() * entry.getValue();
        }
        return carbsTotal;
    }

    @Override
    public double getProtein() {
        double proteinTotal = 0;
        for (Map.Entry<Food, Double> entry : recipe.entrySet()) {
            proteinTotal += entry.getKey().getProtein() * entry.getValue();
        }

        return proteinTotal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe Name: ").append(getName()).append("\n");
        sb.append("Foods for recipe = ");

        for (Map.Entry<Food, Double> entry : getRecipe().entrySet()) {
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