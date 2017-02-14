package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.FeaturedStream;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface FeaturedStreamResponseHandler extends BaseFailureHandler {
    void onSuccess(List<FeaturedStream> streams);
}
