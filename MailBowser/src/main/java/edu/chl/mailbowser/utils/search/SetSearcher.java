package edu.chl.mailbowser.utils.search;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by mats on 05/05/15.
 *
 * A concrete implementation of Searcher that returns sets.
 */
public class SetSearcher<S extends Searchable> implements Searcher<S> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<S> search(Collection<S> collection, String query) {
        return collection.stream()
                .filter(item -> item.matches(query))
                .collect(Collectors.toSet());
    }
}
