/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : Dashboard.java
 * Package : com.mycompany.oop.ui
 */

package com.mycompany.oop.ui;

import com.mycompany.oop.model.User;

/**
 * What every dashboard must be able to do, whatever user level it serves.
 *
 * ManagerDashboardPanel and CashierDashboardPanel are completely separate
 * classes with different menus and different screens, but MainFrame holds
 * whichever one was built in a variable of this type. It therefore never tests
 * which kind it has: calling getDashboardTitle() runs the version belonging to
 * the object actually created, which is polymorphism doing the work that an
 * <code>if</code> on the role name would otherwise do.
 *
 * A third user level could be added later by writing another dashboard that
 * implements this interface, with no change to MainFrame at all.
 *
 * @author The Sweet Cupcake Shop development team
 */
public interface Dashboard {

    /**
     * @return the name of this dashboard, used in the window title
     */
    String getDashboardTitle();

    /**
     * @return the member of staff this dashboard was built for
     */
    User getSignedInUser();

    /**
     * Brings the Dashboard / Home screen to the front. Called once as soon as
     * the dashboard is shown.
     */
    void showHome();
}
