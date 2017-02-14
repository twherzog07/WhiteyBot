package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.ChannelSubscription;

import java.util.List;

/**
 * Created by Travis on 2/11/2017.
 */
public interface ChannelSubscriptionsResponseHandler extends BaseFailureHandler {
    void onSuccess(int total, List<ChannelSubscription> subscriptions);
}
