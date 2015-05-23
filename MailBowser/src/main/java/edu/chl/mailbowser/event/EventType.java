package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 *
 * An enum for all possible event types.
 */
public enum EventType {
    ADD_ACCOUNT,
    REMOVE_ACCOUNT,
    SELECTED_EMAIL,
    ADD_TAG,
    REMOVE_TAG,
    FETCH_EMAIL,
    FETCH_EMAILS,
    FETCH_EMAILS_FAIL,
    SEND_EMAIL,
    SEND_EMAIL_FAIL,
    SELECTED_TAG,
    CLEAR_EMAILS,
    SEARCH,
    CLOSE_THIS,
    GUI_REMOVE_TAG,
    DELETE_TAG,
    NEW_EMAIL,
    REPLY,
    REPLY_ALL,
    FORWARD
}
