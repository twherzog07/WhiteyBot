package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Stream;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface StreamsResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<Stream> streams);
}
