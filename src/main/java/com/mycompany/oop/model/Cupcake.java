/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Cupcake.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

import com.mycompany.oop.exception.ValidationException;

/**
 * One product sold by The Sweet Cupcake Shop.
 *
 * This is the main entity class of the system. Every attribute is private and
 * is reached only through getters and setters, so the object protects its own
 * data: a price can never become negative and a name can never become empty,
 * no matter which part of the program tries to change it.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class Cupcake {

    /** Highest price the shop is willing to record for a single item. */
    public static final double MAX_PRICE = 100000.00;

    private String cupcakeId;
    private String name;
    private Category category;
    private double price;
    private String description;
    private boolean available;

    /**
     * Full constructor. Every value passes through the validating setters, so
     * an invalid Cupcake object can never be constructed.
     *
     * @param cupcakeId   unique identifier such as CK001
     * @param name        flavour or product name
     * @param category    the category the product belongs to
     * @param price       selling price in rupees
     * @param description short description shown to the customer
     * @param available   true when the item is currently in stock
     * @throws ValidationException when any value breaks a business rule
     */
    public Cupcake(String cupcakeId, String name, Category category, double price,
            String description, boolean available) throws ValidationException {
        setCupcakeId(cupcakeId);
        setName(name);
        setCategory(category);
        setPrice(price);
        setDescription(description);
        setAvailable(available);
    }

    /**
     * Convenience constructor used when a new product is added from the user
     * interface. It is an example of constructor overloading: the shorter form
     * simply assumes the item is available and has no description, then hands
     * the work to the full constructor with the this(...) call.
     *
     * @param cupcakeId unique identifier such as CK001
     * @param name      flavour or product name
     * @param category  the category the product belongs to
     * @param price     selling price in rupees
     * @throws ValidationException when any value breaks a business rule
     */
    public Cupcake(String cupcakeId, String name, Category category, double price)
            throws ValidationException {
        this(cupcakeId, name, category, price, "", true);
    }

    /* ------------------------------------------------------------------
     * Encapsulated accessors and mutators
     * ------------------------------------------------------------------ */

    public String getCupcakeId() {
        return cupcakeId;
    }

    public final void setCupcakeId(String cupcakeId) throws ValidationException {
        if (cupcakeId == null || cupcakeId.trim().isEmpty()) {
            throw new ValidationException("Cupcake ID cannot be empty.");
        }
        if (cupcakeId.indexOf('|') >= 0) {
            throw new ValidationException("Cupcake ID cannot contain the pipe character.");
        }
        this.cupcakeId = cupcakeId.trim().toUpperCase();
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Cupcake name cannot be empty.");
        }
        String cleaned = name.trim();
        if (cleaned.length() < 2) {
            throw new ValidationException("Cupcake name must be at least 2 characters long.");
        }
        if (cleaned.indexOf('|') >= 0) {
            throw new ValidationException("Cupcake name cannot contain the pipe character.");
        }
        this.name = cleaned;
    }

    public Category getCategory() {
        return category;
    }

    public final void setCategory(Category category) throws ValidationException {
        if (category == null) {
            throw new ValidationException("Please select a valid category.");
        }
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public final void setPrice(double price) throws ValidationException {
        if (price <= 0) {
            throw new ValidationException("Price must be greater than zero.");
        }
        if (price > MAX_PRICE) {
            throw new ValidationException("Price cannot be more than " + MAX_PRICE + ".");
        }
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public final void setDescription(String description) throws ValidationException {
        String cleaned = (description == null) ? "" : description.trim();
        if (cleaned.indexOf('|') >= 0) {
            throw new ValidationException("Description cannot contain the pipe character.");
        }
        if (cleaned.length() > 120) {
            throw new ValidationException("Description cannot be longer than 120 characters.");
        }
        this.description = cleaned;
    }

    public boolean isAvailable() {
        return available;
    }

    public final void setAvailable(boolean available) {
        this.available = available;
    }

    /* ------------------------------------------------------------------
     * Derived (calculated) values
     * ------------------------------------------------------------------ */

    /**
     * @return the price formatted the way it is displayed in the shop, for
     *         example "Rs. 250.00"
     */
    public String getFormattedPrice() {
        return String.format("Rs. %.2f", price);
    }

    /**
     * @return "Available" or "Out of Stock", ready to be shown in a table
     */
    public String getStatusText() {
        return available ? "Available" : "Out of Stock";
    }

    /**
     * Decides whether this product should appear in the result of a keyword
     * search. Keeping the rule inside the class means every screen searches in
     * exactly the same way.
     *
     * @param keyword text typed by the user
     * @return true when the name, category or description contains the keyword
     */
    public boolean matchesKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        String needle = keyword.trim().toLowerCase();
        return cupcakeId.toLowerCase().contains(needle)
                || name.toLowerCase().contains(needle)
                || category.getDisplayName().toLowerCase().contains(needle)
                || description.toLowerCase().contains(needle);
    }

    @Override
    public String toString() {
        return cupcakeId + " - " + name + " (" + category.getDisplayName() + ") "
                + getFormattedPrice();
    }
}
