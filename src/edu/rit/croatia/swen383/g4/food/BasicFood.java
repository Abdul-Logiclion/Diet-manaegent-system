package edu.rit.croatia.swen383.g4.food;

/**
 * The type Basic food.
 */
public class BasicFood implements Food {


    private String foodName;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;

    /**
     * Instantiates a new Basic food.
     *
     * @param foodName the food name
     * @param calories the calories
     * @param fat      the fat
     * @param carbs    the carbs
     * @param protein  the protein
     */
    public BasicFood(String foodName, double calories, double fat, double carbs, double protein) {
        this.foodName = foodName;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return foodName;
    }

    /**
     * Sets food name.
     *
     * @param foodName the food name
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     * Gets calories.
     *
     * @return the calories
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Sets calories.
     *
     * @param calories the calories
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Gets fat.
     *
     * @return the fat
     */
    public double getFat() {
        return fat;
    }

    /**
     * Sets fat.
     *
     * @param fat the fat
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * Gets carbs.
     *
     * @return the carbs
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * Sets carbs.
     *
     * @param carbs the carbs
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    /**
     * Gets protein.
     *
     * @return the protein
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Sets protein.
     *
     * @param protein the protein
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    @Override
    public String toString() {
        return "Basic Food: \t\n" + "\t Food Name = " + foodName + "\t Calories = " + calories + "\t Fat = " + fat+ "\t Carbs = " + carbs+ "\t Protein = " + protein;
    }
}
