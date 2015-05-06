package edu.chl.mailbowser.search;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by mats on 05/05/15.
 *
 * A class for searching for strings in sets.
 */
public class SetSearch {
    /*
     * private constructor to prevent initialization
     */
    private SetSearch() {}

    /**
     * Takes a set and a query, and returns a new set with all the items that matches the query.
     * It is up to each individual item in the set to determine if it matches the query or not.
     *
     * @param set the set to search in
     * @param query the query to search for
     * @return a new set with all items from the original set that matches the given query
     */
    public static <T extends Searchable> Set<T> search(Set<T> set, String query) {
        return set.stream()
                .filter(item -> item.matches(query))
                .collect(Collectors.toSet());
    }
}
