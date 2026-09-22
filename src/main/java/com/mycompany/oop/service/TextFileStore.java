/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : TextFileStore.java
 * Package : com.mycompany.oop.service
 */

package com.mycompany.oop.service;

import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.util.FileHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Partly finished storage class that knows how to work with a pipe separated
 * text file, but does not know what a record looks like.
 *
 * This class carries out the whole read/write routine once: open the file,
 * ignore blank lines and comment lines, turn each remaining line into an object,
 * report any damaged record, and write everything back with a header line. The
 * two pieces it cannot know - how to turn a line into an object and how to turn
 * an object into a line - are left as abstract methods for the subclasses.
 *
 * UserFileStore and CupcakeFileStore therefore only supply the small part that
 * is different, which removes duplicated file handling code from the system.
 *
 * @param <T> the type of record stored in the file
 * @author The Sweet Cupcake Shop development team
 */
public abstract class TextFileStore<T> implements DataStore<T> {

    /** Character used to separate the fields of one record. */
    protected static final String SEPARATOR = "|";

    /** Same character prepared for use with String.split(). */
    protected static final String SPLIT_PATTERN = "\\|";

    /** Character that marks a comment line at the top of a data file. */
    protected static final String COMMENT_PREFIX = "#";

    private final String fileName;

    /**
     * @param fileName name of the text file inside the data folder
     */
    protected TextFileStore(String fileName) {
        this.fileName = fileName;
    }

    /* ------------------------------------------------------------------
     * The parts each subclass must supply
     * ------------------------------------------------------------------ */

    /**
     * Turns one line of the file into an object.
     *
     * @param fields the record already split on the separator
     * @return the object built from those fields
     * @throws ValidationException when the record is damaged or incomplete
     */
    protected abstract T parse(String[] fields) throws ValidationException;

    /**
     * Turns one object back into a line of the file.
     *
     * @param item the object to save
     * @return the record as a single line of text
     */
    protected abstract String format(T item);

    /**
     * @return the comment line written at the top of the file, which documents
     *         the field order for anyone opening the file in a text editor
     */
    protected abstract String header();

    /* ------------------------------------------------------------------
     * The shared routine, written once for every kind of record
     * ------------------------------------------------------------------ */

    /**
     * Reads the whole file and rebuilds the objects.
     *
     * A missing or empty file gives an empty list rather than an error, so the
     * application starts normally the very first time it is used.
     *
     * @return every valid record found in the file
     * @throws IOException   when the file exists but cannot be read
     * @throws ShopException when a record in the file is damaged
     */
    @Override
    public List<T> loadAll() throws IOException, ShopException {
        List<T> items = new ArrayList<T>();
        List<String> lines = FileHelper.readLines(fileName);

        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;

            // Skip blank lines and the header comment.
            if (line == null || line.trim().isEmpty()
                    || line.trim().startsWith(COMMENT_PREFIX)) {
                continue;
            }

            // -1 keeps trailing empty fields, so a record whose last field is
            // blank is still read with the correct number of columns.
            String[] fields = line.split(SPLIT_PATTERN, -1);
            try {
                items.add(parse(fields));
            } catch (ValidationException e) {
                // The line number is added so a damaged file can be repaired.
                throw new ValidationException("Damaged record in " + fileName
                        + " on line " + lineNumber + ": " + e.getMessage());
            }
        }
        return items;
    }

    /**
     * Writes the complete list back to the file, replacing the old contents.
     *
     * @param items the records to save
     * @throws IOException when the file cannot be written
     */
    @Override
    public void saveAll(List<T> items) throws IOException {
        List<String> lines = new ArrayList<String>();
        lines.add(COMMENT_PREFIX + " " + header());
        for (T item : items) {
            lines.add(format(item));
        }
        FileHelper.writeLines(fileName, lines);
    }

    /**
     * Appends one record to the end of the file.
     *
     * @param item the record to save
     * @throws IOException when the file cannot be written
     */
    @Override
    public void add(T item) throws IOException {
        // Make sure a brand new file starts with its header comment.
        if (!FileHelper.exists(fileName)) {
            List<String> headerOnly = new ArrayList<String>();
            headerOnly.add(COMMENT_PREFIX + " " + header());
            FileHelper.writeLines(fileName, headerOnly);
        }
        FileHelper.appendLine(fileName, format(item));
    }

    /**
     * @return the name of the text file this store is responsible for
     */
    public String getFileName() {
        return fileName;
    }
}
