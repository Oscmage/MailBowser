package edu.chl.mailbowser.tests.mock;

import edu.chl.mailbowser.utils.search.Searchable;

/**
 * Created by mats on 30/05/15.
 */
public class MockSearchable implements Searchable {
    public boolean matchesCalled = false;

    @Override
    public boolean matches(String query) {
        matchesCalled = true;
        if (query.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
