package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Stream;

/**
 * Created by Travis on 2/11/2017.
 */
public interface StreamResponseHandler extends BaseFailureHandler {
    void onSuccess(Stream stream);
}
