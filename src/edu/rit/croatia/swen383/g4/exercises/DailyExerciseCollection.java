package edu.rit.croatia.swen383.g4.exercises;

import edu.rit.croatia.swen383.g4.exercises.DailyExercise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyExerciseCollection {


        private List<DailyExercise> dailyExercises;

        /**
         * Instantiates a new Log collection.
         */
        public DailyExerciseCollection() {
            dailyExercises = new ArrayList<>();
        }

        /**
         * Gets daily logs.
         *
         * @return the daily logs
         */
        public List<DailyExercise> getDailyLogs() {
            return dailyExercises;
        }

        /**
         * Add daily log.
         *
         * @param dailyExercise the daily log
         */
        public void addDailyExercise(DailyExercise dailyExercise) {
            dailyExercises.add(dailyExercise);
        }

        /**
         * Gets daily log by date.
         *
         * @param date the date
         * @return the daily log by date
         */
        public DailyLog getDailyExerciseByDate(LocalDate date) {
            for (DailyExercise dailyExercise : dailyExercises) {
                if (dailyExercise.getDate().equals(date)) {
                    return dailyLog;
                }
            }
            return null;
        }

        /**
         * Remove daily log by date boolean.
         *
         * @param date the date
         * @return the boolean
         */
        public boolean removeDailyLogByDate(LocalDate date) {
            DailyLog dailyLog = getDailyLogByDate(date);
            if (dailyLog != null) {
                dailyLogs.remove(dailyLog);
                return true;
            }
            return false;
        }

        /**
         * Update daily log.
         *
         * @param date       the date
         * @param updatedLog the updated log
         */
        public void updateDailyLog(LocalDate date, DailyLog updatedLog) {
            DailyLog dailyLog = getDailyLogByDate(date);
            if (dailyLog != null) {
                dailyLog.setDate(updatedLog.getDate());
                dailyLog.setWeight(updatedLog.getWeight());
                dailyLog.getIntakeAndServing().clear();
                dailyLog.getIntakeAndServing().putAll(updatedLog.getIntakeAndServing());
            }
        }
    }
}
