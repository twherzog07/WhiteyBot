package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.Channel;

/**
 * Created by Travis on 2/11/2017.
 */
public interface ChannelResponseHandler extends BaseFailureHandler {
    void onSuccess(Channel channel);
}
