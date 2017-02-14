package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.ChannelFollow;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface ChannelFollowsResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<ChannelFollow> follows);
}
