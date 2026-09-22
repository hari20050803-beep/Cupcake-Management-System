/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : FileHelper.java
 * Package : com.mycompany.oop.util
 */

package com.mycompany.oop.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Low level file input and output for the whole application.
 *
 * Every read and every write that the system performs goes through this one
 * class, using the Java stream classes BufferedReader/FileReader for reading and
 * PrintWriter/FileWriter for writing. Keeping the plumbing in a single place
 * means the rest of the program never has to think about streams, closing files
 * or missing folders.
 *
 * The class is declared final and has a private constructor because it is a
 * utility class: it holds no state and is never meant to be instantiated or
 * extended.
 *
 * @author The Sweet Cupcake Shop development team
 */
public final class FileHelper {

    /** Folder, relative to the project folder, in which the data files live. */
    public static final String DATA_FOLDER = "data";

    /**
     * Utility classes must not be instantiated.
     */
    private FileHelper() {
    }

    /**
     * Builds the full path of a data file and makes sure the data folder
     * exists, creating it when the application runs for the very first time.
     *
     * @param fileName for example "users.txt"
     * @return a File object pointing at data/users.txt
     * @throws IOException when the folder cannot be created
     */
    public static File resolveDataFile(String fileName) throws IOException {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                throw new IOException("Unable to create the data folder: "
                        + folder.getAbsolutePath());
            }
        }
        return new File(folder, fileName);
    }

    /**
     * Reads every line of a text file.
     *
     * A missing file is not treated as an error. The first time the shop runs
     * there is nothing saved yet, so an empty list is returned and the caller
     * simply starts with no records. This is the graceful handling of a missing
     * file required by the scenario.
     *
     * @param fileName for example "cupcakes.txt"
     * @return the lines of the file, or an empty list when the file is absent
     * @throws IOException when the file exists but cannot be read
     */
    public static List<String> readLines(String fileName) throws IOException {
        List<String> lines = new ArrayList<String>();
        File file = resolveDataFile(fileName);

        if (!file.exists()) {
            return lines;
        }

        // try-with-resources closes the reader automatically, even if an
        // exception is thrown while reading.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        return lines;
    }

    /**
     * Writes every line to a text file, replacing whatever was there before.
     * The file (and the data folder) is created when it does not exist yet.
     *
     * @param fileName for example "cupcakes.txt"
     * @param lines    the lines to write
     * @throws IOException when the file cannot be written
     */
    public static void writeLines(String fileName, List<String> lines) throws IOException {
        File file = resolveDataFile(fileName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }

    /**
     * Adds a single line to the end of a text file without rewriting the whole
     * file. Used when one new record has to be saved.
     *
     * @param fileName for example "users.txt"
     * @param line     the record to append
     * @throws IOException when the file cannot be written
     */
    public static void appendLine(String fileName, String line) throws IOException {
        File file = resolveDataFile(fileName);

        // The second argument "true" opens the file in append mode.
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(line);
        }
    }

    /**
     * @param fileName for example "users.txt"
     * @return true when the data file already exists on disk
     */
    public static boolean exists(String fileName) {
        return new File(DATA_FOLDER, fileName).exists();
    }

    /**
     * @param fileName for example "users.txt"
     * @return the absolute path of the file, used by the About screen so the
     *         user can see exactly where the data is stored
     */
    public static String absolutePathOf(String fileName) {
        return new File(DATA_FOLDER, fileName).getAbsolutePath();
    }
}
