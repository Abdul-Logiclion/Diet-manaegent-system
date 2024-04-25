package edu.rit.croatia.swen383.g4.logs;

import edu.rit.croatia.swen383.g4.food.Food;
import edu.rit.croatia.swen383.g4.food.Recipe;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Daily log.
 */
public class  DailyLog{

    // intakeAmount is a map of food which contains duplicate values for integers which represent the amount of servings of that food
    private final Map<Food, Double> intakeAmount;
    private LocalDate date;
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
    public Map<Food, Double> getIntakeAndServing() {
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

     */
    public void addFood(Food food, double servings) {
        // Check if the food is already in the log for the day
        if (intakeAmount.containsKey(food)) {
            // If it is, add the new servings to the existing servings
            double existingServings = intakeAmount.get(food);
            intakeAmount.put(food, existingServings + servings);
        } else {
            // If not, simply add the new food with its servings
            intakeAmount.put(food, servings);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: ").append(getDate()).append("\n");
        sb.append("Foods consumed:\n");

        for (Map.Entry<Food, Double> entry : getIntakeAndServing().entrySet()) {
            Food food = entry.getKey();
            sb
                    .append("\t")
                    .append(food.getName())
                    .append(" - Servings: ")
                    .append(entry.getValue())
                    .append("\n");

            if (food instanceof Recipe) {
                Recipe recipe = (Recipe) food;
                for (Map.Entry<Food, Double> recipeEntry : recipe
                        .getRecipe()
                        .entrySet()) {
                    sb
                            .append("\t\t")
                            .append(recipeEntry.getKey().getName())
                            .append(" - Servings: ")
                            .append(recipeEntry.getValue() * entry.getValue())
                            .append("\n");
                }
            }
        }

        return sb.toString();
    }
}
