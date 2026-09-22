/*
 * The Sweet Cupcake Shop  -  CSE4006 Object Oriented Programming (WRIT1)
 *
 * File    : DataStore.java
 * Package : com.mycompany.oop.service
 */

package com.mycompany.oop.service;

import com.mycompany.oop.exception.ShopException;
import java.io.IOException;
import java.util.List;

/**
 * Contract for anything that can store and retrieve the records of the shop.
 *
 * This interface is pure abstraction: it says that the system must be able to
 * load all records, save all records and add one record, but it says nothing
 * about where those records live. The rest of the application is written against
 * this interface only, so the text file storage used today could be replaced
 * with a database later without touching a single screen.
 *
 * @param <T> the type of record being stored, for example User or Cupcake
 * @author The Sweet Cupcake Shop development team
 */
public interface DataStore<T> {

    /**
     * Retrieves every record from the storage.
     *
     * @return all saved records, or an empty list when nothing is saved yet
     * @throws IOException   when the storage cannot be read
     * @throws ShopException when a stored record is not valid
     */
    List<T> loadAll() throws IOException, ShopException;

    /**
     * Replaces the whole contents of the storage.
     *
     * @param items the records to save
     * @throws IOException when the storage cannot be written
     */
    void saveAll(List<T> items) throws IOException;

    /**
     * Saves one extra record, keeping the records that are already stored.
     *
     * @param item the record to save
     * @throws IOException when the storage cannot be written
     */
    void add(T item) throws IOException;
}
