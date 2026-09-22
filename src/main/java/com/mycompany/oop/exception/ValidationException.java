/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : ValidationException.java
 * Package : com.mycompany.oop.exception
 */

package com.mycompany.oop.exception;

/**
 * Thrown when data entered by a user, or a record read from a text file, does
 * not satisfy the rules of the system (empty field, negative price, duplicate
 * identifier, unknown category and so on).
 *
 * @author The Sweet Cupcake Shop development team
 */
public class ValidationException extends ShopException {
    /** Version tag required because Exception implements Serializable. */
    private static final long serialVersionUID = 1L;


    public ValidationException(String message) {
        super(message);
    }
}
