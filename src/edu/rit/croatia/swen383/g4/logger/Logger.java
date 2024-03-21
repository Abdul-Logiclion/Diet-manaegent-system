package edu.rit.croatia.swen383.g4.logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Logger.
 */
public class Logger {

  private static final String LOG_FILENAME = "log.txt";

  /**
   * Log.
   *
   * @param message the message
   */
  public void log(String message) {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(new FileWriter(LOG_FILENAME, true));
      LocalDateTime dateTime = LocalDateTime.now();
      String formattedDateTime = dateTime.format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      );
      writer.println(formattedDateTime + " - " + message);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
  }
}
