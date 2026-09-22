/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : AuthenticationException.java
 * Package : com.mycompany.oop.exception
 */

package com.mycompany.oop.exception;

/**
 * Thrown when a login attempt fails because the username does not exist or the
 * password does not match.
 *
 * The message is deliberately vague ("Invalid username or password") so that an
 * attacker cannot use the error text to discover which usernames are valid.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class AuthenticationException extends ShopException {
    /** Version tag required because Exception implements Serializable. */
    private static final long serialVersionUID = 1L;


    public AuthenticationException(String message) {
        super(message);
    }
}
