/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : CupcakeFileStore.java
 * Package : com.mycompany.oop.service
 */

package com.mycompany.oop.service;

import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Category;
import com.mycompany.oop.model.Cupcake;

/**
 * Stores the products in data/cupcakes.txt.
 *
 * Like UserFileStore this class inherits the whole read/write routine from
 * TextFileStore and only supplies the two record specific methods. The two
 * stores are a clear example of polymorphism: TextFileStore.loadAll() calls
 * parse(), and the version of parse() that actually runs depends on which store
 * object is being used.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class CupcakeFileStore extends TextFileStore<Cupcake> {

    /** Name of the text file that holds the products. */
    public static final String FILE_NAME = "cupcakes.txt";

    /** Number of fields in one product record. */
    private static final int FIELD_COUNT = 6;

    /**
     * Normal constructor, which uses data/cupcakes.txt.
     */
    public CupcakeFileStore() {
        super(FILE_NAME);
    }

    /**
     * Overloaded constructor that points the store at a different file inside
     * the data folder. Used by the automated tests to read a deliberately
     * damaged file without touching the real catalogue.
     *
     * @param fileName name of the file to use instead of cupcakes.txt
     */
    public CupcakeFileStore(String fileName) {
        super(fileName);
    }

    /**
     * Rebuilds a Cupcake object from one line of the file.
     *
     * @param fields cupcakeId, name, category, price, description, available
     * @return the product described by that line
     * @throws ValidationException when the record is incomplete, the category
     *                             is unknown or the price is not a number
     */
    @Override
    protected Cupcake parse(String[] fields) throws ValidationException {
        if (fields.length < FIELD_COUNT) {
            throw new ValidationException("expected " + FIELD_COUNT
                    + " fields but found " + fields.length);
        }

        String cupcakeId = fields[0].trim();
        String name = fields[1].trim();

        Category category = Category.fromText(fields[2]);
        if (category == null) {
            throw new ValidationException("unknown category '" + fields[2].trim() + "'");
        }

        double price;
        try {
            price = Double.parseDouble(fields[3].trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("price '" + fields[3].trim()
                    + "' is not a number");
        }

        String description = fields[4].trim();
        boolean available = Boolean.parseBoolean(fields[5].trim());

        return new Cupcake(cupcakeId, name, category, price, description, available);
    }

    /**
     * Turns a Cupcake object into one line of the file.
     *
     * @param cupcake the product to save
     * @return cupcakeId|name|CATEGORY|price|description|available
     */
    @Override
    protected String format(Cupcake cupcake) {
        return cupcake.getCupcakeId() + SEPARATOR
                + cupcake.getName() + SEPARATOR
                + cupcake.getCategory().name() + SEPARATOR
                + String.format("%.2f", cupcake.getPrice()) + SEPARATOR
                + cupcake.getDescription() + SEPARATOR
                + cupcake.isAvailable();
    }

    @Override
    protected String header() {
        return "cupcakeId|name|category|price|description|available";
    }
}
