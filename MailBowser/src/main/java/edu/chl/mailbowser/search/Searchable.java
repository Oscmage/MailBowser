package edu.chl.mailbowser.search;

/**
 * Created by mats on 05/05/15.
 *
 * An interface that enables searching of the objects that implements it.
 */
@FunctionalInterface
public interface Searchable {
    /**
     * Checks whether or not this object matches the query. It is up to each individual implementation
     * to decide how to do the matching.
     *
     * @param query the query to match against
     * @return true if the query matches the object, otherwise false
     */
    boolean matches(String query);
}
