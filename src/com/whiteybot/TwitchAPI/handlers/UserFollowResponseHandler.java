package com.whiteybot.TwitchAPI.handlers;

import com.whiteybot.TwitchAPI.models.UserFollow;

/**
 * Created by Travis on 2/11/2017.
 */
public interface UserFollowResponseHandler extends BaseFailureHandler {
    void onSuccess(UserFollow follow);
}
