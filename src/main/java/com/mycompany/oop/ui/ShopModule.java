/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : ShopModule.java
 * Package : com.mycompany.oop.ui
 */

package com.mycompany.oop.ui;

/**
 * What every working screen of the dashboard must be able to do.
 *
 * The dashboard holds its screens in a list of ShopModule and never knows which
 * particular screen it is holding. When a menu button is pressed it simply calls
 * refresh() on the one it is about to show, and Java runs the version belonging
 * to the real object. That is runtime polymorphism, and it is what lets a new
 * screen be added to the system without changing the dashboard at all.
 *
 * An interface is used rather than a shared parent class because each screen is
 * a NetBeans Form Editor panel and therefore already extends JPanel. Java allows
 * only one superclass but any number of interfaces, so this is the form of
 * abstraction that fits.
 *
 * @author The Sweet Cupcake Shop development team
 */
public interface ShopModule {

    /**
     * @return the name of the screen, used as its menu button label
     */
    String getModuleTitle();

    /**
     * Reloads the screen from its data source, so the user always sees what is
     * really saved rather than what was on screen last time.
     */
    void refresh();
}
