/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Category.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

/**
 * Product categories used to group the shop's products.
 *
 * The categories come directly from the assessment scenario: classic flavours,
 * seasonal specials, custom orders and gluten-free items, plus the beverages
 * and dessert combos that the shop also sells.
 *
 * @author The Sweet Cupcake Shop development team
 */
public enum Category {

    CLASSIC_FLAVOURS("Classic Flavours"),
    SEASONAL_SPECIALS("Seasonal Specials"),
    CUSTOM_ORDERS("Custom Orders"),
    GLUTEN_FREE("Gluten Free"),
    BEVERAGES("Beverages"),
    DESSERT_COMBOS("Dessert Combos");

    private final String displayName;

    private Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a stored keyword (for example "GLUTEN_FREE") back into a
     * Category. Used when a record is read from cupcakes.txt.
     *
     * @param text keyword read from the data file
     * @return the matching Category, or null when the keyword is invalid
     */
    public static Category fromText(String text) {
        if (text == null) {
            return null;
        }
        String cleaned = text.trim().toUpperCase().replace(' ', '_');
        for (Category category : Category.values()) {
            if (category.name().equals(cleaned)) {
                return category;
            }
        }
        return null;
    }

    /**
     * Finds a category using the label shown on screen, which is what the
     * combo boxes in the user interface display.
     *
     * @param displayName label such as "Seasonal Specials"
     * @return the matching Category, or null when not found
     */
    public static Category fromDisplayName(String displayName) {
        if (displayName == null) {
            return null;
        }
        for (Category category : Category.values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName.trim())) {
                return category;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
