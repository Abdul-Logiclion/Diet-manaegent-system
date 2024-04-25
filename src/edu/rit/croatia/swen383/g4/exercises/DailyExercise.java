package edu.rit.croatia.swen383.g4.exercises;


import edu.rit.croatia.swen383.g4.food.Food;
import edu.rit.croatia.swen383.g4.food.Recipe;
import edu.rit.croatia.swen383.g4.logs.DailyLog;
import edu.rit.croatia.swen383.g4.util.CsvHandler;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Daily log.
 */
public class  DailyExercise implements  Exercise{



//    private static final String LOG_FILE_PATH = "log.csv";
//    private static final String EXERCISE_FILE_PATH = "exercise.csv";
//
//
//
//    public double calculateTotalBurntCaloriesOnDate(String selectedDate, double w) {
//        double totalBurntCalories = 0.0;
//        // Read exercise calorie data from exercise.csv and store in a map
//        Map<String, Double> exerciseCaloriesMap = readExerciseCaloriesFromCSV();
//        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 6) {
//                    LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
//                    if (date.toString().equals(selectedDate)) {
//                        if (parts[3].equals("e") && exerciseCaloriesMap.containsKey(parts[4])) {
//                            double caloriesPerHour = exerciseCaloriesMap.get(parts[4]);
//                            double fractionalHours = Double.parseDouble(parts[5]) / 60.0;
//                            double burntCalories = caloriesPerHour * w * fractionalHours;
//                            totalBurntCalories += burntCalories;
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return totalBurntCalories;
//    }
//
//    private Map<String, Double> readExerciseCaloriesFromCSV() {
//        Map<String, Double> exerciseCaloriesMap = new HashMap<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(EXERCISE_FILE_PATH))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//
//                String[] parts = line.split(",");
//                if (parts.length == 3 && parts[0].equals("e")) {
//                    String exerciseName = parts[1];
//                    double calories = Double.parseDouble(parts[2]);
//
//                    exerciseCaloriesMap.put(exerciseName, calories);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return exerciseCaloriesMap;
//    }
//
//
//
//
//
//
//    private List writeExerciseDataToLog(String selectedDate, String exerciseName, double burnCalories, List<String> exercises) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.csv", true))) {
//
//            LocalDate date = LocalDate.parse(selectedDate);
//            String formattedDate = String.format("%d,%02d,%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
//
//// Write the formatted data to the file
//            writer.write(formattedDate + ",e," + exerciseName + "," + burnCalories + "\n");
//
//// Add the formatted data to the exercises list
//            String exerciseEntry = formattedDate + ",e," + exerciseName + "," + burnCalories;
//            exercises.add(exerciseEntry);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return exercises;
//    }
//
//
//    private static final Map<String, Double> exerciseCaloriesMap = new HashMap<>();
//
//    // Static block to initialize the exercise calories map
//    static {
//        try (BufferedReader br = new BufferedReader(new FileReader("exercise.csv"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 3) {
//                    String exerciseName = parts[1].trim();
//                    double calories = Double.parseDouble(parts[2].trim());
//                    exerciseCaloriesMap.put(exerciseName, calories);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Function to get calories for a given exercise name
//    private double getCaloriesForExercise(String exerciseName) {
//        return exerciseCaloriesMap.getOrDefault(exerciseName, 0.0);
//    }


        private String exerciseName;
        private double calories;

        /**
         * Instantiates new Basic food.
         *
         * @param exerciseName the food name
         * @param calories the calories
         */

        public DailyExercise(
                String exerciseName,
                double calories) {
            this.exerciseName = exerciseName;
            this.calories = calories;

        }

        /**
         * @return
         */
        @Override
        public String getName() {
            return exerciseName;
        }

        /**
         * Sets food name.
         *
         * @param foodName the food name
         */
        public void setExerciseName(String foodName) {
            this.exerciseName = exerciseName;
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


        @Override
        public String toString() {
            return (
                    "Exercise: \t\n" +
                            "\t Exercise Name = " +
                            getName() +
                            "\t Calories = " +
                            getCalories()
            );
        }
    }
