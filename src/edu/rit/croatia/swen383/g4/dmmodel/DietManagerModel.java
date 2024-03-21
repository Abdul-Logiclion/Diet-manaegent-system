package edu.rit.croatia.swen383.g4.dmmodel;

import edu.rit.croatia.swen383.g4.food.BasicFood;
import edu.rit.croatia.swen383.g4.food.Food;
import edu.rit.croatia.swen383.g4.food.FoodCollection;
import edu.rit.croatia.swen383.g4.food.Recipe;
import edu.rit.croatia.swen383.g4.logs.DailyLog;
import edu.rit.croatia.swen383.g4.logs.LogCollection;
import edu.rit.croatia.swen383.g4.user.User;
import edu.rit.croatia.swen383.g4.util.CsvHandler;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Diet manager model.
 */
public class DietManagerModel {

    

    /**
     * Instantiates a new Diet manager model.
     *
     * @param foodCsvFile the food csv file
     * @param logCsvFile  the log csv file
     */
    public DietManagerModel(String foodCsvFile, String logCsvFile) {
        
    }

    /**
     * Add basic food.
     *
     * @param food the food
     */
    public void addBasicFood(BasicFood food) {
        
    }

    /**
     * Add recipe.
     *
     * @param recipe the recipe
     */
    public void addRecipe(Recipe recipe) {
       
    }

    /**
     * Gets food.
     *
     * @return the food
     */
    public List<Food> getFood() {
        return null;
        
    }

    /**
     * Gets basic food by name.
     *
     * @param foodName the food name
     * @return the basic food by name
     */
    public BasicFood getBasicFoodByName(String foodName) {
        return null;
        
    }


    /**
     * Gets food by name.
     *
     * @param foodName the food name
     * @return the food by name
     */
    public Food getFoodByName(String foodName) {
        return null;
        
    }

    /**
     * Gets daily log for today.
     *
     * @return the daily log for today
     */
    public DailyLog getDailyLogForToday() {
        return null;

    }

    /**
     * Gets daily log by date.
     *
     * @param date the date
     * @return the daily log by date
     */
    public DailyLog getDailyLogByDate(LocalDate date) {
        return null;

    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return null;
    
    }

    /**
     * Gets log collection.
     *
     * @return the log collection
     */
    public LogCollection getLogCollection() {
        return null;
    
    }

    private void loadCsvData() {
   
    }

    /**
     * Save csv data.
     */
    public void saveCsvData() {
    
    }


}
