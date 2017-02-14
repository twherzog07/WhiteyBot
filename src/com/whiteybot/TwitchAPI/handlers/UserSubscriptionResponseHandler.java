package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.UserSubscription;

/**
 * Created by Travis on 2/11/2017.
 */
public interface UserSubscriptionResponseHandler extends BaseFailureHandler {
    void onSuccess(UserSubscription subscription);
}
