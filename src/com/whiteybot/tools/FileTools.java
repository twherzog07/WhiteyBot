package com.whiteybot.tools;

import static com.whiteybot.tools.LogTools.logMessage;
import static com.whiteybot.tools.LogTools.logError;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * Created by Travis on 2/10/2017.
 */
public class FileTools {
    public static ArrayList<String> readTextFile(String file) {
        ArrayList<String> result = new ArrayList<String>();

        File f = new File(file);

        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();

                while (line != null) {
                    result.add(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                exit(1);
            }
        }

        return result;
    }

    public static void removeTextFromFile(String directory, String file, String line) {
        File f = new File(directory, file);

        if (!f.exists()) {
            logError("Unable to remove text from file (" + f.getAbsolutePath() + ") because it does not exist!");
            return;
        }

        ArrayList<String> lines = readTextFile(f.getAbsolutePath());

        if (lines.isEmpty()) {
            logError("Unable to remove text from file (" + f.getAbsolutePath() + ") because it is empty!");
            return;
        }

        ArrayList<String> removed_lines = new ArrayList<>();
        for (String s : lines) {
            if (s.equals(line)) {
                logMessage("Removing line " + s + " from " + file + "!");
                removed_lines.add(s);
            }
        }

        lines.removeAll(removed_lines);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(f.getAbsolutePath(), false));
            for (String s : lines)
                writer.println(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTextToFile(String directory, String file, String text) {
        File dir = new File(directory);
        File f = new File(directory , file);

        // If the directory does not exist, create it
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (SecurityException e) {
                logError("Failed to create directory! - " + directory);
                e.printStackTrace();
                return;
            }
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(f, true));
            writer.println(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
