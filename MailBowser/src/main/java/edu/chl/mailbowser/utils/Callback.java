package edu.chl.mailbowser.utils;

/**
 * Created by mats on 07/05/15.
 */
public interface Callback<E> {
    void onSuccess(E object);
    void onFailure(String msg);
}
