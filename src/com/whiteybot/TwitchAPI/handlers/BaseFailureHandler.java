package com.whiteybot.TwitchAPI.handlers;

/**
 * Created by Travis on 2/11/2017.
 */
public interface BaseFailureHandler {
    void onFailure(int statusCode, String statusMessage, String errorMessage);
    void onFailure(Throwable throwable);
}
