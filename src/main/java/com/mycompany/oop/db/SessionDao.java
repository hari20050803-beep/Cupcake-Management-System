/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : SessionDao.java
 * Package : com.mycompany.oop.db
 */

package com.mycompany.oop.db;

import com.mycompany.oop.model.LoginSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * All of the SQL for the login_session table.
 *
 * A row is inserted the moment somebody logs in, and the same row is updated
 * with the logout time when they leave. That gives the shop a complete history
 * of who used the till and when, which a text file would not hold conveniently.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class SessionDao {

    private final Database database;

    /**
     * @param database supplies the JDBC connections
     */
    public SessionDao(Database database) {
        this.database = database;
    }

    /**
     * Records that somebody has just logged in.
     *
     * @param accountId the account that signed in
     * @param username  their login name
     * @return the primary key of the new session row, or -1 when unknown
     * @throws SQLException when the database cannot be written
     */
    public int startSession(int accountId, String username) throws SQLException {
        String sql = "INSERT INTO login_session (account_id, username, login_at)"
                + " VALUES (?, ?, NOW())";

        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, accountId);
            ps.setString(2, username);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * Records that a session has finished.
     *
     * @param sessionId the session returned by startSession
     * @throws SQLException when the database cannot be written
     */
    public void endSession(int sessionId) throws SQLException {
        String sql = "UPDATE login_session SET logout_at = NOW()"
                + " WHERE session_id = ? AND logout_at IS NULL";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sessionId);
            ps.executeUpdate();
        }
    }

    /**
     * Closes any session left open for an account, used when the application was
     * shut down without logging out properly.
     *
     * @param accountId the account to tidy up
     * @throws SQLException when the database cannot be written
     */
    public void closeOpenSessions(int accountId) throws SQLException {
        String sql = "UPDATE login_session SET logout_at = NOW()"
                + " WHERE account_id = ? AND logout_at IS NULL";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.executeUpdate();
        }
    }

    /**
     * Reads the login history, most recent first.
     *
     * @param limit largest number of rows to return
     * @return the recent sessions
     * @throws SQLException when the database cannot be read
     */
    public List<LoginSession> findRecent(int limit) throws SQLException {
        String sql = "SELECT session_id, account_id, username, login_at, logout_at"
                + " FROM login_session ORDER BY login_at DESC, session_id DESC LIMIT ?";

        List<LoginSession> sessions = new ArrayList<LoginSession>();
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sessions.add(new LoginSession(
                            rs.getInt("session_id"),
                            rs.getInt("account_id"),
                            rs.getString("username"),
                            rs.getTimestamp("login_at"),
                            rs.getTimestamp("logout_at")));
                }
            }
        }
        return sessions;
    }

    /**
     * Counts the sessions that have no logout time, which is the number of
     * people signed in at this moment.
     *
     * @return the number of open sessions
     * @throws SQLException when the database cannot be read
     */
    public int countActiveSessions() throws SQLException {
        String sql = "SELECT COUNT(*) FROM login_session WHERE logout_at IS NULL";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
