package edu.chl.mailbowser.utils.search;

import java.util.Collection;

/**
 * Created by mats on 27/05/15.
 *
 * A function interface for searchers. A searcher has only one method that takes a collection and a query,
 * and returns a new collection with all the items that matches the query. It is up to each individual item in the
 * collection to determine if it matches the query or not.
 */
@FunctionalInterface
public interface Searcher<S extends Searchable> {
    /**
     * Takes a collection and a query, and returns a new collection with all the items that matches
     * the query. It is up to each individual item in the collection to determine if it matches the query or not.
     *
     * @param collection the set to search in
     * @param query the query to search for
     * @return a new collection with all items from the original collection that matches the given query
     */
    Collection<S> search(Collection<S> collection, String query);
}
