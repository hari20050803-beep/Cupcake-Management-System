/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : DataSeeder.java
 * Package : com.mycompany.oop.service
 */

package com.mycompany.oop.service;

import com.mycompany.oop.exception.ShopException;
import com.mycompany.oop.model.Cashier;
import com.mycompany.oop.model.Category;
import com.mycompany.oop.model.Cupcake;
import com.mycompany.oop.model.Manager;
import com.mycompany.oop.model.User;
import com.mycompany.oop.util.PasswordUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Puts the starting data in place the first time the application is run.
 *
 * Without this class a brand new installation would have no accounts at all and
 * nobody could log in. It creates one Manager account, one Cashier account and a
 * small product list taken from the shop description in the scenario.
 *
 * It only ever writes when a file is empty, so the data a user adds later is
 * never overwritten when the application restarts.
 *
 * @author The Sweet Cupcake Shop development team
 */
public final class DataSeeder {

    private DataSeeder() {
    }

    /**
     * Creates the starting accounts when no account exists yet.
     *
     * @param userStore storage for the staff accounts
     * @return true when the starting accounts had to be created
     * @throws IOException   when the file cannot be written
     * @throws ShopException when a starting account is invalid
     */
    public static boolean seedUsers(DataStore<User> userStore)
            throws IOException, ShopException {

        if (!userStore.loadAll().isEmpty()) {
            return false;
        }

        List<User> users = new ArrayList<User>();
        users.add(new Manager("U001", "manager", "manager@sweetcupcake.lk",
                PasswordUtil.hash("manager123")));
        users.add(new Cashier("U002", "kasun", "kasun@sweetcupcake.lk",
                PasswordUtil.hash("cashier123")));
        userStore.saveAll(users);
        return true;
    }

    /**
     * Creates the starting product list when no product exists yet.
     *
     * @param cupcakeStore storage for the products
     * @return true when the starting products had to be created
     * @throws IOException   when the file cannot be written
     * @throws ShopException when a starting product is invalid
     */
    public static boolean seedCupcakes(DataStore<Cupcake> cupcakeStore)
            throws IOException, ShopException {

        if (!cupcakeStore.loadAll().isEmpty()) {
            return false;
        }

        List<Cupcake> cupcakes = new ArrayList<Cupcake>();

        cupcakes.add(new Cupcake("CK001", "Classic Vanilla", Category.CLASSIC_FLAVOURS,
                250.00, "Vanilla sponge with vanilla buttercream swirl", true));
        cupcakes.add(new Cupcake("CK002", "Rich Chocolate", Category.CLASSIC_FLAVOURS,
                280.00, "Dark chocolate sponge with chocolate ganache", true));
        cupcakes.add(new Cupcake("CK003", "Red Velvet", Category.CLASSIC_FLAVOURS,
                320.00, "Red velvet sponge with cream cheese frosting", true));
        cupcakes.add(new Cupcake("CK004", "Fruity Delight", Category.CLASSIC_FLAVOURS,
                300.00, "Mixed fruit sponge topped with fresh fruit", true));
        cupcakes.add(new Cupcake("CK005", "Christmas Spice", Category.SEASONAL_SPECIALS,
                380.00, "Cinnamon and nutmeg sponge, seasonal decoration", true));
        cupcakes.add(new Cupcake("CK006", "Mango Season Special", Category.SEASONAL_SPECIALS,
                360.00, "Fresh mango cream, available in mango season", false));
        cupcakes.add(new Cupcake("CK007", "Birthday Custom Box", Category.CUSTOM_ORDERS,
                1500.00, "Box of six cupcakes with a personalised message", true));
        cupcakes.add(new Cupcake("CK008", "Gluten Free Almond", Category.GLUTEN_FREE,
                340.00, "Almond flour sponge, no wheat flour used", true));
        cupcakes.add(new Cupcake("CK009", "Vanilla Milkshake", Category.BEVERAGES,
                420.00, "Thick vanilla milkshake served cold", true));
        cupcakes.add(new Cupcake("CK010", "Cupcake and Coffee Combo", Category.DESSERT_COMBOS,
                550.00, "One classic cupcake with a regular hot coffee", true));

        cupcakeStore.saveAll(cupcakes);
        return true;
    }
}
