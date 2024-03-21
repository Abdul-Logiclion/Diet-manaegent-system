package edu.rit.croatia.swen383.g4.logs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Log collection.
 */
public class LogCollection {

  private List<DailyLog> dailyLogs;

  /**
   * Instantiates a new Log collection.
   */
  public LogCollection() {
    dailyLogs = new ArrayList<>();
  }

  /**
   * Gets daily logs.
   *
   * @return the daily logs
   */
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

  /**
   * Gets daily log by date.
   *
   * @param date the date
   * @return the daily log by date
   */
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
      dailyLog.getIntakeAmount().clear();
      dailyLog.getIntakeAmount().putAll(updatedLog.getIntakeAmount());
    }
  }
}