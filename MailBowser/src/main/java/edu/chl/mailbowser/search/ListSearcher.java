package edu.chl.mailbowser.search;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mats on 27/05/15.
 *
 * An implementation of Searcher that returns lists.
 */
public class ListSearcher<S extends Searchable> implements Searcher<S> {
    /**
     * {@inheritDoc}
     */
    @Override
    public List<S> search(Collection<S> collection, String query) {
        return collection.stream()
                .filter(item -> item.matches(query))
                .collect(Collectors.toList());
    }
}
