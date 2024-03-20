package edu.rit.croatia.swen383.g4.logs;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogCollection {
    private List<DailyLog> dailyLogs;


    public LogCollection() {
        dailyLogs = new ArrayList<>();
    }


    public List<DailyLog> getDailyLogs() {
        return dailyLogs;
    }

    /**
     * Add daily log.
     *
     * @param dailyLog the daily log
     */
    public void addDailyLog(DailyLog dailyLog) {
        dailyLogs.add(dailyLog);
    }

  
    public DailyLog getDailyLogByDate(LocalDate date) {
        for (DailyLog dailyLog : dailyLogs) {
            if (dailyLog.getDate().equals(date)) {
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
            dailyLog.getIntake().clear();
            dailyLog.getIntake().putAll(updatedLog.getIntake());
        }
    }

}