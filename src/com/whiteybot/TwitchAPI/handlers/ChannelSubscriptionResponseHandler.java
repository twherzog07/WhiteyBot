package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.ChannelSubscription;

/**
 * Created by Travis on 2/11/2017.
 */
public interface ChannelSubscriptionResponseHandler extends BaseFailureHandler {
    void onSuccess(ChannelSubscription subscription);
}
