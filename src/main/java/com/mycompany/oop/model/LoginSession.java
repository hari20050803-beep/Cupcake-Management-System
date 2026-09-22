/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : LoginSession.java
 * Package : com.mycompany.oop.model
 */

package com.mycompany.oop.model;

import java.sql.Timestamp;

/**
 * One visit to the system, as stored in the MySQL login_session table.
 *
 * A row is written the moment a member of staff logs in, and the same row is
 * completed with the time when they log out. A row whose logout time is still
 * empty therefore means that person is signed in at this moment, which is how
 * the system tracks who is currently logged in.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class LoginSession {

    private int sessionId;
    private int accountId;
    private String username;
    private Timestamp loginAt;
    private Timestamp logoutAt;

    /**
     * @param sessionId primary key from the database
     * @param accountId the account this session belongs to
     * @param username  login name, copied in so the history stays readable
     * @param loginAt   time the person logged in
     * @param logoutAt  time they logged out, or null if still signed in
     */
    public LoginSession(int sessionId, int accountId, String username,
            Timestamp loginAt, Timestamp logoutAt) {

        this.sessionId = sessionId;
        this.accountId = accountId;
        this.username = username;
        this.loginAt = loginAt;
        this.logoutAt = logoutAt;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getLoginAt() {
        return loginAt;
    }

    public Timestamp getLogoutAt() {
        return logoutAt;
    }

    /**
     * @return true when this session has not been closed by a logout
     */
    public boolean isActive() {
        return logoutAt == null;
    }

    /**
     * @return the logout time, or "still signed in" when the session is open
     */
    public String getLogoutText() {
        return (logoutAt == null) ? "still signed in" : logoutAt.toString();
    }

    /**
     * Works out how long the visit lasted.
     *
     * @return the length of the session in minutes, or -1 when it is still open
     */
    public long getDurationMinutes() {
        if (logoutAt == null) {
            return -1;
        }
        long millis = logoutAt.getTime() - loginAt.getTime();
        return millis / 60000L;
    }

    /**
     * @return the duration as text, ready to show in a table
     */
    public String getDurationText() {
        long minutes = getDurationMinutes();
        if (minutes < 0) {
            return "-";
        }
        return minutes + " min";
    }

    @Override
    public String toString() {
        return username + "  " + loginAt + "  ->  " + getLogoutText();
    }
}
