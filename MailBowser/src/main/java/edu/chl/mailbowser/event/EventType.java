package edu.chl.mailbowser.event;

/**
 * Created by mats on 29/04/15.
 *
 * An enum for all possible event types.
 */
public enum EventType {
    ADD_ACCOUNT,
    REMOVE_ACCOUNT,
    SELECT_TAG,
    ADD_TAG_TO_EMAIL,
    ADDED_TAG_TO_EMAIL,
    REMOVE_TAG_FROM_EMAIL,
    REMOVED_TAG_FROM_EMAIL,
    DELETE_TAG,
    DELETED_TAG,
    SELECT_EMAIL,
    FETCHED_EMAIL,
    FETCHED_ALL_EMAILS,
    FETCHED_ALL_EMAILS_FAILED,
    SEND_EMAIL,
    SEND_EMAIL_FAIL,
    CLEAR_EMAILS,
    MARK_EMAIL_AS_DELETED,
    SEARCH,
    OPEN_COMPOSE_EMAIL_WINDOW,
    OPEN_COMPOSE_EMAIL_WINDOW_FORWARD,
    OPEN_COMPOSE_EMAIL_WINDOW_REPLY,
    OPEN_COMPOSE_EMAIL_WINDOW_REPLY_ALL,
    OPEN_ADD_TAG_WINDOW,
    OPEN_CONTACT_BOOK, FXML_LOADED
}
