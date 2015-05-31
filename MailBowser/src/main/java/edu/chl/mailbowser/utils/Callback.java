package edu.chl.mailbowser.utils;

/**
 * Created by mats on 07/05/15.
 *
 * An interface for callbacks to enable asynchronous requests. Objects of this interface are often sent as parameters
 * to asynchronous methods, to provide a way of telling the requester when a request has finished.
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
