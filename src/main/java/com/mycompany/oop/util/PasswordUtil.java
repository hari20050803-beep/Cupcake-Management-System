/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : PasswordUtil.java
 * Package : com.mycompany.oop.util
 */

package com.mycompany.oop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Turns a plain text password into a SHA-256 hash.
 *
 * Passwords are never stored in readable form. When a user logs in, the typed
 * password is hashed again and the two hashes are compared, so the real password
 * exists only for the moment it is being checked. This protects the staff
 * accounts even if somebody opens data/users.txt in Notepad.
 *
 * @author The Sweet Cupcake Shop development team
 */
public final class PasswordUtil {

    private PasswordUtil() {
    }

    /**
     * Hashes a password using the SHA-256 algorithm from the standard Java
     * security library.
     *
     * @param plainPassword the password typed by the user
     * @return the hash as a 64 character lower case hexadecimal string
     */
    public static String hash(String plainPassword) {
        String source = (plainPassword == null) ? "" : plainPassword;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(source.getBytes("UTF-8"));

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xff & bytes[i]);
                if (hex.length() == 1) {
                    builder.append('0');
                }
                builder.append(hex);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is part of every standard Java installation, so this can
            // only happen on a broken platform. Failing loudly is safer than
            // silently storing an unhashed password.
            throw new IllegalStateException("SHA-256 algorithm is not available.", e);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding is not available.", e);
        }
    }

    /**
     * Compares a typed password against a stored hash.
     *
     * @param plainPassword the password typed at the login screen
     * @param storedHash    the hash read from data/users.txt
     * @return true when the password is correct
     */
    public static boolean matches(String plainPassword, String storedHash) {
        if (storedHash == null) {
            return false;
        }
        return hash(plainPassword).equalsIgnoreCase(storedHash.trim());
    }

    /* ------------------------------------------------------------------
     * Salted hashing, used by the MySQL account table
     * ------------------------------------------------------------------ */

    /**
     * Creates a new random salt.
     *
     * A salt is a short random value stored beside the hash. Because every
     * account gets a different salt, two people who happen to choose the same
     * password still end up with different hashes, so the stored values cannot
     * be compared or looked up in a precomputed table.
     *
     * @return a 32 character hexadecimal salt
     */
    public static String newSalt() {
        byte[] bytes = new byte[16];
        new java.security.SecureRandom().nextBytes(bytes);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                builder.append('0');
            }
            builder.append(hex);
        }
        return builder.toString();
    }

    /**
     * Hashes a password together with its salt.
     *
     * @param plainPassword the password typed by the user
     * @param salt          the salt stored with the account
     * @return the SHA-256 hash of salt + password
     */
    public static String hashWithSalt(String plainPassword, String salt) {
        String safeSalt = (salt == null) ? "" : salt;
        String safePassword = (plainPassword == null) ? "" : plainPassword;
        return hash(safeSalt + safePassword);
    }

    /**
     * Compares a typed password against a salted hash.
     *
     * @param plainPassword the password typed by the user
     * @param salt          the salt stored with the account
     * @param storedHash    the hash stored with the account
     * @return true when the password is correct
     */
    public static boolean matchesSalted(String plainPassword, String salt,
            String storedHash) {
        if (storedHash == null) {
            return false;
        }
        return hashWithSalt(plainPassword, salt).equalsIgnoreCase(storedHash.trim());
    }
}
