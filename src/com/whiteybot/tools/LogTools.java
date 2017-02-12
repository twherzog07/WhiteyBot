package com.whiteybot.tools;

import static com.whiteybot.tools.Globals.*;
import static com.whiteybot.tools.FileTools.writeTextToFile;

import java.util.Date;

/**
 * Created by Travis on 2/10/2017.
 */
public class LogTools {

    public static void logError(String error) {
        logError("logs", "error", error);
    }

    public static void logError(String directory, String file, String error) {
        Date currentDate = new Date();
        System.out.println("[ERR]: " + error);
        writeTextToFile(directory, file + "_" + gDateFormat.format(currentDate) + ".txt", gTimeFormat.format(currentDate) + ": " + error);
    }

    public static void logMessage(String msg) {
        logMessage("logs", "log", msg);
    }

    public static void logMessage(String directory, String file, String msg) {
        Date currentDate = new Date();
        System.out.println("[LOG]: " + msg);
        writeTextToFile(directory, file + "_" + gDateFormat.format(currentDate) + ".txt", gTimeFormat.format(currentDate) + ": " + msg);
    }
}
