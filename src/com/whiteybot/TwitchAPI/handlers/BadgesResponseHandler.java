package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.ChannelBadges;

/**
 * Created by Travis on 2/11/2017.
 */
public interface BadgesResponseHandler extends BaseFailureHandler {
    void onSuccess(ChannelBadges badges);
}
