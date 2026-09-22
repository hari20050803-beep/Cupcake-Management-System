/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : CupcakeDao.java
 * Package : com.mycompany.oop.db
 */

package com.mycompany.oop.db;

import com.mycompany.oop.exception.ValidationException;
import com.mycompany.oop.model.Category;
import com.mycompany.oop.model.Cupcake;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All of the SQL for the cupcake table.
 *
 * The text file stays the master copy of the catalogue, because the assessment
 * requires the products to be saved to and read from a file. This table is a
 * copy of that catalogue inside MySQL, which the shop can query with SQL and
 * report on. The copy is refreshed by pressing the sync button on the database
 * screen, and each row records when it was copied and by whom.
 *
 * @author The Sweet Cupcake Shop development team
 */
public class CupcakeDao {

    private final Database database;

    /**
     * @param database supplies the JDBC connections
     */
    public CupcakeDao(Database database) {
        this.database = database;
    }

    /**
     * Copies one product into the database, inserting it or updating the row
     * that is already there.
     *
     * ON DUPLICATE KEY UPDATE means the statement works whether the product is
     * new or has changed, so the sync does not need to check first.
     *
     * @param cupcake  the product to store
     * @param syncedBy username of the member of staff doing the sync
     * @throws SQLException when the database cannot be written
     */
    public void save(Cupcake cupcake, String syncedBy) throws SQLException {
        String sql = "INSERT INTO cupcake "
                + "(cupcake_id, name, category, price, description, available,"
                + " synced_at, synced_by) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?) "
                + "ON DUPLICATE KEY UPDATE name = VALUES(name),"
                + " category = VALUES(category), price = VALUES(price),"
                + " description = VALUES(description),"
                + " available = VALUES(available), synced_at = NOW(),"
                + " synced_by = VALUES(synced_by)";

        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cupcake.getCupcakeId());
            ps.setString(2, cupcake.getName());
            ps.setString(3, cupcake.getCategory().name());
            ps.setDouble(4, cupcake.getPrice());
            ps.setString(5, cupcake.getDescription());
            ps.setBoolean(6, cupcake.isAvailable());
            ps.setString(7, syncedBy);
            ps.executeUpdate();
        }
    }

    /**
     * Copies a whole list of products into the database in one transaction.
     *
     * Auto commit is switched off so that either every row is written or none
     * is. If a row fails half way through, the rollback leaves the table exactly
     * as it was, so the copy can never be left half finished.
     *
     * @param cupcakes the products to store
     * @param syncedBy username of the member of staff doing the sync
     * @return the number of rows written
     * @throws SQLException when the database cannot be written
     */
    public int saveAll(List<Cupcake> cupcakes, String syncedBy) throws SQLException {
        String sql = "INSERT INTO cupcake "
                + "(cupcake_id, name, category, price, description, available,"
                + " synced_at, synced_by) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?) "
                + "ON DUPLICATE KEY UPDATE name = VALUES(name),"
                + " category = VALUES(category), price = VALUES(price),"
                + " description = VALUES(description),"
                + " available = VALUES(available), synced_at = NOW(),"
                + " synced_by = VALUES(synced_by)";

        Connection conn = null;
        try {
            conn = database.getConnection();
            conn.setAutoCommit(false);

            int written = 0;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (Cupcake cupcake : cupcakes) {
                    ps.setString(1, cupcake.getCupcakeId());
                    ps.setString(2, cupcake.getName());
                    ps.setString(3, cupcake.getCategory().name());
                    ps.setDouble(4, cupcake.getPrice());
                    ps.setString(5, cupcake.getDescription());
                    ps.setBoolean(6, cupcake.isAvailable());
                    ps.setString(7, syncedBy);
                    ps.addBatch();
                }
                int[] results = ps.executeBatch();
                written = results.length;
            }
            conn.commit();
            return written;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackProblem) {
                    // Nothing more can be done here; the original error is the
                    // one worth reporting, so it is rethrown below.
                }
            }
            throw e;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeProblem) {
                    // Ignored: the connection is being discarded anyway.
                }
            }
        }
    }

    /**
     * Reads every product stored in the database.
     *
     * @return the products in the cupcake table
     * @throws SQLException        when the database cannot be read
     * @throws ValidationException when a stored row breaks a product rule
     */
    public List<Cupcake> findAll() throws SQLException, ValidationException {
        String sql = "SELECT cupcake_id, name, category, price, description,"
                + " available FROM cupcake ORDER BY cupcake_id";

        List<Cupcake> cupcakes = new ArrayList<Cupcake>();
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category category = Category.fromText(rs.getString("category"));
                if (category == null) {
                    throw new ValidationException("The cupcake table contains an"
                            + " unknown category for " + rs.getString("cupcake_id"));
                }
                cupcakes.add(new Cupcake(
                        rs.getString("cupcake_id"),
                        rs.getString("name"),
                        category,
                        rs.getDouble("price"),
                        rs.getString("description") == null
                                ? "" : rs.getString("description"),
                        rs.getBoolean("available")));
            }
        }
        return cupcakes;
    }

    /**
     * Searches the database by category using SQL rather than Java.
     *
     * @param category the category to filter by
     * @return the matching products
     * @throws SQLException        when the database cannot be read
     * @throws ValidationException when a stored row breaks a product rule
     */
    public List<Cupcake> findByCategory(Category category)
            throws SQLException, ValidationException {

        String sql = "SELECT cupcake_id, name, category, price, description,"
                + " available FROM cupcake WHERE category = ? ORDER BY cupcake_id";

        List<Cupcake> cupcakes = new ArrayList<Cupcake>();
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cupcakes.add(new Cupcake(
                            rs.getString("cupcake_id"),
                            rs.getString("name"),
                            category,
                            rs.getDouble("price"),
                            rs.getString("description") == null
                                    ? "" : rs.getString("description"),
                            rs.getBoolean("available")));
                }
            }
        }
        return cupcakes;
    }

    /**
     * Removes one product from the database copy.
     *
     * @param cupcakeId the product to remove
     * @return true when a row was actually removed
     * @throws SQLException when the database cannot be written
     */
    public boolean delete(String cupcakeId) throws SQLException {
        String sql = "DELETE FROM cupcake WHERE cupcake_id = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cupcakeId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * @return the number of products stored in the database
     * @throws SQLException when the database cannot be read
     */
    public int count() throws SQLException {
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT COUNT(*) FROM cupcake");
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /**
     * Uses SQL to work out the average price in each category, something the
     * database does more neatly than Java.
     *
     * @return one line of text per category
     * @throws SQLException when the database cannot be read
     */
    public List<String> categoryPriceReport() throws SQLException {
        String sql = "SELECT category, COUNT(*) AS items, AVG(price) AS average,"
                + " MIN(price) AS cheapest, MAX(price) AS dearest"
                + " FROM cupcake GROUP BY category ORDER BY category";

        List<String> lines = new ArrayList<String>();
        try (Connection conn = database.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category category = Category.fromText(rs.getString("category"));
                String label = (category == null)
                        ? rs.getString("category") : category.getDisplayName();

                lines.add(String.format("%-20s items: %-3d  average: Rs. %8.2f"
                        + "  lowest: Rs. %8.2f  highest: Rs. %8.2f",
                        label, rs.getInt("items"), rs.getDouble("average"),
                        rs.getDouble("cheapest"), rs.getDouble("dearest")));
            }
        }
        return lines;
    }
}
