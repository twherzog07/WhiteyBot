package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Video;

/**
 * Created by Travis on 2/11/2017.
 */
public interface VideoResponseHandler extends BaseFailureHandler {
    void onSuccess(Video video);
}
