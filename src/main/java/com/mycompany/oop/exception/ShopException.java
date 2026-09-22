/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : ShopException.java
 * Package : com.mycompany.oop.exception
 */

package com.mycompany.oop.exception;

/**
 * Base class for every error that the Sweet Cupcake Shop application raises on
 * purpose.
 *
 * Having one common parent means the user interface can write a single
 * catch (ShopException e) block and still show the correct message for a
 * validation error, a failed login or a blocked action. This is polymorphism
 * applied to exception handling.
 *
 * It extends Exception (not RuntimeException) so that the compiler forces every
 * caller to deal with the problem.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class ShopException extends Exception {
    /** Version tag required because Exception implements Serializable. */
    private static final long serialVersionUID = 1L;


    /**
     * @param message message that is safe to display to the user
     */
    public ShopException(String message) {
        super(message);
    }

    /**
     * @param message message that is safe to display to the user
     * @param cause   the original lower level exception
     */
    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }
}
