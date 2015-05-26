package edu.chl.mailbowser.utils;

/**
 * Created by mats on 07/05/15.
 */
public interface Callback<E> {
    /**
     * Method to be called on successfully run
     * Each class is responsible for its own implementation
     */
    void onSuccess(E object);

    /**
     * Method to be called on unsuccessfully run
     * Each class is responsible for its own implementation
     * @param msg failure message
     */
    void onFailure(String msg);
}
