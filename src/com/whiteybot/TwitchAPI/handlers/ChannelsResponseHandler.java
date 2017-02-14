package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Channel;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface ChannelsResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<Channel> channels);
}
