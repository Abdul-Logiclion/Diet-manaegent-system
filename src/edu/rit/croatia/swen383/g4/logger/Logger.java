package edu.rit.croatia.swen383.g4.logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Logger class is responsible for creating log messages with timestamps.
 * These messages are then written to a specified log file. This class is used
 * to log various events and errors throughout the application lifecycle.
 */
public class Logger {
    // The name of the log file where log entries will be written.
    private static final String LOG_FILENAME = "log.txt";

    /**
     * Logs a message with the current date and time to the log file.
     *
     * @param message the message to be logged. This is the content that will be
     *                appended to the log file, preceded by the current timestamp.
     */
    public void log(String message) {
        PrintWriter writer = null;
        try {
            // Opens the log file in append mode, ensuring that existing content is not overwritten.
            writer = new PrintWriter(new FileWriter(LOG_FILENAME, true));
            // Gets the current date and time.
            LocalDateTime dateTime = LocalDateTime.now();
            // Formats the current date and time as a string.
            String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            // Writes the formatted date and time, followed by the message, to the log file.
            writer.println(formattedDateTime + " - " + message);
            // Ensures that the message is immediately written to the file.
            writer.flush();
        } catch (IOException e) {
            // Prints the stack trace of the exception to standard error if an I/O error occurs.
            e.printStackTrace();
        } finally {
            // Closes the PrintWriter, releasing any system resources associated with it.
            if (writer != null) {
                writer.close();
            }
        }
    }
}
