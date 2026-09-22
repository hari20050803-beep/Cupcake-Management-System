/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : AccessDeniedException.java
 * Package : com.mycompany.oop.exception
 */

package com.mycompany.oop.exception;

/**
 * Thrown when a signed-in user tries to perform an action that their user level
 * does not allow, for example a Cashier trying to create a new user account.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class AccessDeniedException extends ShopException {
    /** Version tag required because Exception implements Serializable. */
    private static final long serialVersionUID = 1L;


    public AccessDeniedException(String message) {
        super(message);
    }
}
