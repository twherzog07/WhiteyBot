package com.whiteybot.TwitchAPI.handlers;

/**
 * Created by Travis on 2/11/2017.
 */
public interface BadgesResponseHandler extends BaseFailureHandler {
    void onSuccess(ChannelBadges badges);
}
