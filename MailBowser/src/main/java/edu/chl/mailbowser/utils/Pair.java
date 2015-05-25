package edu.chl.mailbowser.utils;

/**
 * Created by mats on 22/05/15.
 *
 * A generic class for modelling a pair of two objects.
 */
public class Pair<U, V> {
    private U first;
    private V second;

    /**
     * Creates a pair with two elements.
     *
     * @param first the first element
     * @param second the second element
     */
    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first element of the pair.
     *
     * @return the first element of the pair
     */
    public U getFirst() {
        return this.first;
    }

    /**
     * Returns the second element of the pair.
     *
     * @return the second element of the pair
     */
    public V getSecond() {
        return this.second;
    }
}
