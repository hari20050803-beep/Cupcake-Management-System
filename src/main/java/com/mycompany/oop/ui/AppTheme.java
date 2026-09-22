/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : AppTheme.java
 * Package : com.mycompany.oop.ui
 */

package com.mycompany.oop.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Shared colours, fonts and small helpers for the screens.
 *
 * The screens themselves are built with the NetBeans Form Editor, so their
 * layout and most of their colours are set in the Design tab. This class holds
 * the few things the Design tab cannot do: the shop logo, which is drawn with
 * Java 2D instead of being loaded from a picture file, and the table styling
 * that has to be applied in code.
 *
 * Drawing the logo in code means the project has no external image files, so it
 * still runs correctly after being copied to another computer. The lecturer's
 * example used a fixed path such as C:\Users\ebi\Desktop\oop\12.1.png, which
 * only works on the machine it was written on.
 *
 * @author The Sweet Cupcake Shop development team
 */
public final class AppTheme {

    /** Main brand colour, used for headers and primary buttons. */
    public static final Color PRIMARY = new Color(140, 47, 84);

    /** Darker shade of the brand colour, used for the menu strip. */
    public static final Color PRIMARY_DARK = new Color(105, 33, 62);

    /** Warm caramel accent, used for the cupcake wrapper. */
    public static final Color ACCENT = new Color(214, 150, 102);

    /** Page background. */
    public static final Color BACKGROUND = new Color(250, 247, 248);

    /** Background of panels and tables. */
    public static final Color CARD = Color.WHITE;

    /** Normal text colour. */
    public static final Color TEXT = new Color(45, 40, 43);

    /** Secondary text colour for hints and captions. */
    public static final Color MUTED = new Color(122, 112, 117);

    /** Colour for success messages. */
    public static final Color SUCCESS = new Color(28, 116, 76);

    /** Colour for error messages. */
    public static final Color ERROR = new Color(176, 42, 48);

    /** Colour of separating lines and borders. */
    public static final Color BORDER = new Color(226, 217, 221);

    /** Alternate row colour that makes long tables easier to read. */
    public static final Color ROW_ALT = new Color(250, 245, 247);

    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);

    private AppTheme() {
    }

    /* ------------------------------------------------------------------
     * Buttons
     * ------------------------------------------------------------------ */

    /**
     * Makes a button appear in the brand colour.
     *
     * The colours are chosen in the Design tab, but the Windows look and feel
     * draws its own button shape and ignores setBackground(), which would leave
     * white text on a white button. Installing the basic button UI makes the
     * colour actually paint, so the button looks the same on every computer.
     *
     * @param button the button to style
     */
    public static void primaryButton(javax.swing.JButton button) {
        paint(button, PRIMARY, PRIMARY_DARK, Color.WHITE);
    }

    /**
     * Makes a button appear as a plain outlined button, for secondary actions.
     *
     * @param button the button to style
     */
    public static void secondaryButton(javax.swing.JButton button) {
        paint(button, CARD, new Color(242, 235, 238), TEXT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(7, 15, 7, 15)));
    }

    /**
     * Makes a button appear as a left aligned dashboard menu item.
     *
     * @param button the button to style
     */
    public static void navButton(javax.swing.JButton button) {
        paint(button, PRIMARY_DARK, PRIMARY, Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
    }

    /**
     * Shared button painting, including a simple hover effect.
     */
    private static void paint(final javax.swing.JButton button,
            final Color background, final Color hover, Color foreground) {

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setOpaque(true);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(hover);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(background);
            }
        });
    }

    /**
     * Applies the shop styling to a results table.
     *
     * The Windows look and feel paints its own column header and ignores
     * setBackground(), which would leave white text on a white strip. A renderer
     * of our own is installed instead so the column names are always readable.
     *
     * @param table        the table to style
     * @param rightAligned index of a column to right align, or -1 for none
     */
    public static void styleTable(JTable table, final int rightAligned) {
        table.setFont(FONT_BODY);
        table.setRowHeight(26);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(246, 228, 235));
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 30));

        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean selected, boolean focused, int row, int column) {
                JLabel label = new JLabel(value == null ? "" : value.toString());
                label.setOpaque(true);
                label.setBackground(PRIMARY);
                label.setForeground(Color.WHITE);
                label.setFont(FONT_SUBHEADING);
                label.setBorder(BorderFactory.createEmptyBorder(6, 9, 6, 9));
                return label;
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean selected, boolean focused, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        t, value, selected, focused, row, column);

                if (!selected) {
                    c.setBackground(row % 2 == 0 ? CARD : ROW_ALT);
                    c.setForeground(TEXT);
                }
                setHorizontalAlignment(column == rightAligned
                        ? SwingConstants.RIGHT : SwingConstants.LEFT);
                setBorder(BorderFactory.createEmptyBorder(0, 9, 0, 12));
                return c;
            }
        });
    }

    /**
     * Draws the shop logo, a small cupcake, using Java 2D.
     *
     * The shape is designed on a 64 by 64 grid and then scaled, so one method
     * produces the logo at any size without a picture file.
     *
     * @param size width and height of the icon in pixels
     * @return the cupcake icon
     */
    public static ImageIcon cupcakeIcon(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        double scale = size / 64.0;
        g.scale(scale, scale);

        // Paper wrapper drawn as a trapezium.
        Polygon wrapper = new Polygon(
                new int[]{15, 49, 43, 21},
                new int[]{35, 35, 61, 61}, 4);
        g.setColor(ACCENT);
        g.fill(wrapper);

        // Pleats on the wrapper.
        g.setColor(new Color(188, 126, 82));
        g.setStroke(new BasicStroke(1.6f));
        g.drawLine(26, 36, 24, 60);
        g.drawLine(32, 36, 32, 60);
        g.drawLine(38, 36, 40, 60);

        // Three swirls of frosting.
        g.setColor(new Color(248, 214, 225));
        g.fillOval(13, 21, 38, 20);
        g.fillOval(17, 12, 30, 19);
        g.fillOval(23, 5, 18, 15);

        // Highlight on the frosting.
        g.setColor(new Color(253, 235, 241));
        g.fillOval(20, 15, 12, 8);

        // Cherry on top.
        g.setColor(new Color(198, 54, 66));
        g.fillOval(27, 1, 10, 10);

        g.dispose();
        return new ImageIcon(image);
    }
}
