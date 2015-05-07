package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 *
 * An enum for all possible event types.
 */
public enum EventType {
    ADD_ACCOUNT,
    ADD_TAG,
    REMOVE_TAG,
    FETCH_EMAILS,
    FETCH_EMAILS_FAIL,
    SEND_EMAIL,
    SEND_EMAIL_FAIL
}
